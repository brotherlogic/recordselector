git fetch -p
git merge origin/master
git gc
rm recordselector.jar
rsync -avz --progress --delete-after 192.168.86.64:codestore/recordselector/ ./codestore
ln -s $(find codestore/ -printf "%T+\t%p\n" | sort | grep with-dependencies | awk '{ print $2 }' | grep .jar$ | tail -n 1) ./recordselector.jar
