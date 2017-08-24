# Octopus-toolkit
======================================================
## 0.Octopus-toolkit Homepage
======================================================

- Homepage : http://octopus-toolkit2.readthedocs.io/en/latest/

======================================================
## 1.Hardware/Software Requirement
======================================================

Minimum Memory (RAM)
- 32Gb memory for RNA-Seq.
- 8Gb memory for Others (ChIP,ATAC,Mnase,Dnase,MeDIP)

Operating System
- 64bit Linux
- Operating System Version:
- Linux : Ubuntu (14.04),(16.04, highly recommend), Fedora (22),(25), Mint (18)

======================================================
## 2.Requirement
======================================================

### Ubuntu, Mint (Ubuntu 16.04, Mint 18)
Java
- $ sudo apt-get install openjdk-8-jdk

Library
- $ sudo apt-get install zlib1g-dev
- $ sudo apt-get install libpng12-dev
- $ sudo apt-get install libncurses5-dev
- $ sudo apt-get install build-essential

OR 

- $ sudo apt-get install zlib1g-dev libpng12-dev libncurses5-dev build-essential

R
- $ sudo apt-get install r-base

======================================================

### Ubuntu (Ubuntu 14.04)
Java
- $ sudo add-apt-repository ppa:openjdk-r/ppa
- $ sudo apt-get update
- $ sudo apt-get install openjdk-8-jdk
- $ sudo update-alternatives --config java
- $ sudo update-alternatives --config javac

Library
- $ sudo apt-get install zlib1g-dev
- $ sudo apt-get install libpng12-dev
- $ sudo apt-get install libncurses5-dev
- $ sudo apt-get install build-essential
- $ sudo apt-get install liblzma-dev
- $ sudo apt-get install libbz2-dev

OR 

- $ sudo apt-get install zlib1g-dev libpng12-dev libncurses5-dev build-essential liblzma-dev libbz2-dev

R
- $ sudo apt-get update
- $ sudo apt-get install r-base
- $ sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E084DAB9
- $ sudo add-apt-repository ppa:marutter/rdev
- $ sudo apt-get update
- $ sudo apt-get upgrade
- $ sudo apt-get install r-base

======================================================

### Fedora (Fedora 25)
Java
- $ sudo yum install java-1.8.0-openjdk

Library
- $ sudo yum install zlib-devel.x86_64
- $ sudo yum install libpng-devel.x86_64
- $ sudo yum install libpng12-devel.x86_64
- $ sudo yum install ncurses-devel.x86_64
- $ sudo yum install gcc-c++
- $ sudo yum install bzip2-devel
- $ sudo yum install xz-devel

OR

- $ sudo yum install zlib-devel.x86_64 libpng-devel.x86_64 libpng12-devel.x86_64 ncurses-devel.x86_64 gcc-c++ bzip2-devel xz-devel

R
- $ sudo yum install R

======================================================
## 3.How to run
======================================================

- $ cd Octopus-toolkit/
- $ java -jar Octopus-toolkit.jar

======================================================
## 4. Contact us
======================================================

- Help : http://octopus-toolkit2.readthedocs.io/en/latest/help.html
- E-mail : Octopustoolkit@gmail.com

======================================================

