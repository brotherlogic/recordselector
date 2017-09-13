git fetch -p
git merge origin/master
git gc
rm recordselector.jar
ln -s $(find target/ | grep jar-with-dependencies.jar$ | sort -n | tail -n 1) ./recordselector.jar
