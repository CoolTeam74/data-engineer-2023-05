import sys

d = {}

for line in sys.stdin:
    key, value = line.strip().split(' ')
    sum = int(value)
    e = d.get(key)
    if e == None:
        count = 1
    else:
        sum = e[0]+sum
        count = e[1]+1
    d.update({key:[sum, count]})
