import os

lines_before = len(os.popen('find ./target').readlines())
os.popen('./syncer.sh').readlines()
lines_after = len(os.popen('find ./target').readlines())

if lines_before != lines_after:
    print "Running!"
    #os.popen('sudo reboot').readlines()
