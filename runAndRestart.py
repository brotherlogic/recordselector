import os

lines_before = os.popen('find ./target | grep jar-with').readlines()[0]
os.popen('./syncer.sh').readlines()
lines_after = len(os.popen('find ./target | grep jar-with').readlines()[0]

print lines_before,lines_after
if lines_before != lines_after:
    os.popen('sudo reboot').readlines()
