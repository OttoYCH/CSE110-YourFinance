# CSE110_YourFinance
Each team please create your own develop branch, don't work on master branch directly.

1. Create a folder for our CSE110 project
2. git init
3. git remote add origin git@github.com:OttoYCH/CSE110-YourFinance.git
4. git clone git@github.com:OttoYCH/CSE110-YourFinance.git
5. Here, use git branch to check if you are on master branch (If not, use git checkout master)
6. git checkout -b "your branch name" e.g. git checkout -b dev-database
7. Then you can start work on your own branch

# Some Tips
After you clone the repository, you want to checkout the branch to work on. Take database team as an example, in your local repository, you should use "git checkout dev-database" which will copy the whole that branch to local repository. Then do "git pull", it may ask you to set upstream which means connecting github/dev-database with local/dev-database. In this way, you type git push or git pull later on won't need to specify repository name and branch name.

Next, each time before you want to push your code to the branch, please "git pull" first in case that your code is not the latest version on github.

When you are sure that everything works fine and want to let everyone to keep developing on it, you should "git pull origin master" first to update whole thing on master branch and solve conflicts. Finally, make pull request on github that you want to merge to master branch. Other people will look into your code and leave comments. Then you are good to merge your code to master branch.
