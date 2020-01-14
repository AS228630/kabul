#include <pthread.h>

typedef struct ThinNode {//узел
    int key;//ключ 
    int val;//значение
    struct ThinNode *next;//указатель на следующий
    pthread_mutex_t mutex;//мьютекс
} ThinNode;

typedef struct ThinList {
    ThinNode *head;//голова
    ThinNode *tail;//хвост
} ThinList;

typedef struct {
    char exists;
    int value;
} FindResult;

char insert(ThinList *list, int key, int value);

FindResult find(ThinList *list, int key);

char myRemove(ThinList *list, int key);

ThinList *init_list(void);

ThinList *get_snapshot(ThinList *list);