git fetch -p
git merge origin/master
git gc
rm recordselector.jar
cat ~/.ssh/id_rsa.pub
rsync -avz --progress --delete-after 192.168.86.64:codestore/recordselector/ ./codestore
ln -s $(find codestore/ | grep jar-with-dependencies.jar$ | sort -n | tail -n 1) ./recordselector.jar
