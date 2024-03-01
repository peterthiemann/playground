## represent lists by dictionaries

empty_list = {'root': None}

def list_insert(lst, data):
    new_elem = {'head': data, 'tail': lst['root']}
    lst['root'] = new_elem

l = empty_list
list_insert(l, 345)
list_insert(l, 4711)

## with TM
class Atomic:
    ...

def atomically(fun):
    ...

## inside atomic transaction
empty_list_tm = Atomic({'root': None})

def list_insert_tm(lst, data):
    def helper():
        new_elem = Atomic({'head': data, 'tail': lst['root']})
        lst['root'] = new_elem
    atomically(helper)

