#pragma once

#include <functional>
#include <future>
#include <mutex>
#include <thread>
#include <utility>
#include <vector>
#include "ConcurrentQueue.h"

using namespace std;

class ThreadPool {//класс потоковый пул
private:
  class ThreadWorker {//класс потоковы рабочий
  private:
    int m_id;//идентификатор
    ThreadPool * m_pool;//пул
  public:
    ThreadWorker(ThreadPool * pool, const int id)
      : m_pool(pool), m_id(id) {//конструктор с параметрами
    }

    void operator()() {
      function<void()> func;
      bool dequeued;
      while (!m_pool->m_shutdown) {
        {
          unique_lock<mutex> lock(m_pool->m_conditional_mutex);
          if (m_pool->m_queue.empty()) {
            m_pool->m_conditional_lock.wait(lock);
          }
          dequeued = m_pool->m_queue.dequeue(func);
        }
        if (dequeued) {
          func();
        }
      }
    }
  };

  bool m_shutdown;
  ConcurrentQueue<function<void()>> m_queue;
  vector<thread> m_threads;
  mutex m_conditional_mutex;
  condition_variable m_conditional_lock;

public:
  explicit ThreadPool(const int n_threads)
    : m_threads(vector<thread>(n_threads)), m_shutdown(false) {
  }//конструктор, задающий число потоков

  void init() {
    for (int i = 0; i < m_threads.size(); ++i) {
      m_threads[i] = thread(ThreadWorker(this, i));
    }//инициализация
  }

  void shutdown() {
    m_shutdown = true;//выключить поток
    m_conditional_lock.notify_all();

    for (auto & m_thread : m_threads) {
      if(m_thread.joinable()) {
        m_thread.join();
      }
    }
  }

    template<typename F, typename...Args>
    auto add_task(F&& f, Args&&... args) -> std::future<decltype(f(args...))> {
        // Create a function with bounded parameters ready to execute
        std::function<decltype(f(args...))()> func = std::bind(std::forward<F>(f), std::forward<Args>(args)...);
        // Encapsulate it into a shared ptr in order to be able to copy construct / assign
        auto task_ptr = std::make_shared<std::packaged_task<decltype(f(args...))()>>(func);

        // Wrap packaged task into void function
        std::function<void()> wrapper_func = [task_ptr]() {
            (*task_ptr)();
        };

        // Enqueue generic wrapper function
        m_queue.enqueue(wrapper_func);

        // Wake up one thread if its waiting
        m_conditional_lock.notify_one();

        // Return future from promise
        return task_ptr->get_future();
    }
};