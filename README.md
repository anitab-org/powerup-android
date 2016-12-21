# PowerUp - Android

PowerUp is a female empowerment educational mobile game app that will allow young girls to learn about reproductive health 
and self-esteem by navigating the life of their avatar!

## Setup for Developers
1. Make sure you have downloaded the latest version of [Android Studio](https://developer.android.com/sdk/index.html). It works on Linux, Windows and Mac. Download the correct version for your OS
1. Go to [the project repo](https://github.com/systers/powerup-android/) and fork it by clicking "Fork" 
1. If you are working on Windows, download [Git Bash for Windows](https://git-for-windows.github.io/) to get a full Unix bash with Git functionality
1. Clone the repo to your desktop `git clone https://github.com/YOUR_USERNAME/powerup-android.git`
1. Initialize Git. `git init`
1. Open the project with Android Studio 

## Configure remotes
When a repository is cloned, it has a default remote called `origin` that points to your fork on GitHub, not the original repository it was forked from. To keep track of the original repository, you should add another remote named `upstream`:

1. Open terminal or git bash in your local repository and set up the origin:

   `git remote add origin https://github.com/YOUR_USERNAME/powerup-android.git`

1. Set the `upstream`:

   `git remote add upstream https://github.com/systers/powerup-android.git`
  
1. Run `git remote -v` to check the status, you should see something like the following:

  > origin    https://github.com/YOUR_USERNAME/powerup-android.git (fetch)
  
  > origin    https://github.com/YOUR_USERNAME/powerup-android.git (push)
  
  > upstream  https://github.com/systers/powerup-android.git (fetch)
  
  > upstream  https://github.com/systers/powerup-android.git (push)

1. To update your local copy with remote changes, run the following:

   `git fetch upstream`

   `git merge upstream/master`

   This will give you an exact copy of the current remote, make sure you don't have any local changes.

## Contributing and developing a feature
1. Make sure you are in the master branch `git checkout master`
1. Sync your copy `git pull`
1. Create a new branch with a meaningful name `git checkout -b branch_name`
1. Develop your feature on Android Studio and run it using the emulator or connecting your own Android device
1. Clean your project from Android Studio `Build/Clean project`
1. Add the files you changed `git add file_name` (avoid using `git add .`)
1. Commit your changes `git commit -m "Message briefly explaining the feature"`
1. Keep one commit per feature. If you forgot to add changes, you can edit the previous commit `git commit --amend`
1. Push to your repo `git push origin branch-name`
1. Go into [the Github repo](https://github.com/systers/powerup-android/) and create a pull request explaining your changes
1. If you are requested to make changes, edit your commit using `git commit --amend`, push again and the pull request will edit automatically
1. You will need to add a message on the pull request notifying your changes to your reviewer

## Contributing Guidelines 
[Click](https://github.com/systers/powerup-android/wiki/How-to-Contribute) here to find the contributing guidelines for the project and follow them before sending a contribution.

## Documentation of PowerUp (Android)
Here's the link to the official documentation:
[Visit Documentation!](http://chetnagsocpowerupandroid.blogspot.in/2015/05/database-design.html)

##Coding Guidelines
1. Don't use magic numbers or hard-coded strings. Put them in dimens.xml or strings.xml
1. Class names should be in CamelCase. Name activities with names including Activity so it's easier to know what they are.
1. Include spaces between parameters when you call a method for example: `Intent(MainActivity.this, GameActivity.class)`.
1. Give relevant names to buttons and other resources. 
1. Use `@id` instead of `@+id` when referring to resources that have been already created in xml files.
