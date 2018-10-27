git fetch -p > /dev/null 2>&1
git merge origin/master
git gc
rm recordselector.jar
ssh-keygen -f "/home/simon/.ssh/known_hosts" -R [73.162.90.182]:2223
rsync -e 'ssh -p 2223 -o StrictHostKeyChecking=no' -avz --delete-after --progress $(curl -s "$1"/resolve | awk -F ':' '{print $1}'):codestore/recordselector/ ./codestore
ln -s $(find codestore/ -printf "%T+\t%p\n" | sort | grep with-dependencies | awk '{ print $2 }' | grep .jar$ | tail -n 1) ./recordselector.jar
