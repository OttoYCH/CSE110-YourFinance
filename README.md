#CSE110_YourFinance
Everyone please create your own develop branch,
don't work on master branch directly.
e.g. git checkout -b dev-otto

This command will switch you to a new branch called dev-otto

1. Adding a remote to our repository
# Set a new remote
git remote add origin git@github.com:OttoYCH/CSE110_y-fi.git

# Check if it works
git remote -v

If it shows like below, it works:

origin   git@github.com:OttoYCH/CSE110_y-fi.git (fetch)
origin  git@github.com:OttoYCH/CSE110_y-fi.git (push)

2. Copy the repo & Create your own branch
git clone git@github.com:OttoYCH/CSE110_y-fi.git

!! important
# Check which branch you are currently at
git branch

# Be sure to be at master branch (e.g. git checkout master)
# In this way, you will copy all contents in master branch
git checkout -b dev-otto (replace otto with your name or whatever you want)

3. Work on your branch
Develop your code in your own branch. After testing, make a pull request to merge to master branch.
Then anyone who do the following work must use 'git pull master' command first to make sure you work on the latest version
