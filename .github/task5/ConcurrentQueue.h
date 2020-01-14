#pragma once

#include <mutex>
#include <queue>
#include "ConcurrentQueue.h"

using namespace std;

template <typename T>
class ConcurrentQueue {//структура очереди
private:
  queue<T> m_queue;
  mutex m_mutex;//мьютекс
public:
  ConcurrentQueue() = default;

  bool empty() {//проверить на пустоту список
    unique_lock<mutex> lock(m_mutex);
    return m_queue.empty();//вернуть значение пустоты
  }

  bool enqueue(T& t) {
    unique_lock<mutex> lock(m_mutex);
    m_queue.push(t);//затолкать новый узел
      return true;
  }
  
  bool dequeue(T& t) {
    unique_lock<mutex> lock(m_mutex);

    if (m_queue.empty()) {
      return false;
    }
    t = move(m_queue.front());
    
    m_queue.pop();
    return true;
  }
};