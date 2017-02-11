=============
0.Quick Start
=============

We provide you with run commands for each OS for fast execution.

0-1. Installation Movie
^^^^^^^^^^^^^^^^^^^^^^^

Add youtube url

.. list-table::
   :widths: 5 20 20 20
   :header-rows: 1

   * - No
     - Sample
     - Genome
     - UCSC
   * - 1
     - MCF7_ER_FullMedia_3hr-r1
     - hg38
     - `UCSC bigWig <http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg38&position=chr21:33038447-33041505&hgct_customText=track%20type=bigWig%20name=MCF7_ER_FullMedia_3hr-r1%20description=%22custom%20bigWig%20track%22%20visibility=full%20bigDataUrl=http://dkucombio.ipdisk.co.kr/publist/VOL1/Public/001_BreastCancerCell/JC1523_MCF7_ER_Full_Media_3hr-r1.CH.hg38.bigWig>`_
   * - 2
     - JC1524_MCF7_ER_Progesterone_3hr-r1
     - hg38
     - `UCSC bigWig <http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg38&position=chr21:33038447-33041505&hgct_customText=track%20type=bigWig%20name=MCF7_ER_Progesterone_3hr-r1%20description=%22custom%20bigWig%20track%22%20visibility=full%20bigDataUrl=http://dkucombio.ipdisk.co.kr/publist/VOL1/Public/001_BreastCancerCell/JC1524_MCF7_ER_Progesterone_3hr-r1.CH.hg38.bigWig>`_
   * - 3
     - JC1525_MCF7_ER_R5020_3hr-r1
     - hg38
     - `UCSC bigWig <http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg38&position=chr21:33038447-33041505&hgct_customText=track%20type=bigWig%20name=MCF7_ER_R5020_3hr-r1%20description=%22custom%20bigWig%20track%22%20visibility=full%20bigDataUrl=http://dkucombio.ipdisk.co.kr/publist/VOL1/Public/001_BreastCancerCell/JC1525_MCF7_ER_R5020_3hr-r1.CH.hg38.bigWig>`_
   * - 4
     - MCF7_PR_FullMedia_3hr-r1
     - hg38
     - `UCSC bigWig <http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg38&position=chr21:33038447-33041505&hgct_customText=track%20type=bigWig%20name=MCF7_ER_FullMedia_3hr-r1%20description=%22custom%20bigWig%20track%22%20visibility=full%20bigDataUrl=http://dkucombio.ipdisk.co.kr/publist/VOL1/Public/001_BreastCancerCell/JC1526_MCF7_PR_Full_Media_3hr-r1.CH.hg38.bigWig>`_
   * - 5
     - JC1527_MCF7_PR_Progesterone_3hr-r1
     - hg38
     - `UCSC bigWig <http://genome.ucsc.edu/cgi-bin/hgTracks?db=hg38&position=chr21:33038447-33041505&hgct_customText=track%20type=bigWig%20name=MCF7_PR_Progesterone_3hr-r1%20description=%22custom%20bigWig%20track%22%20visibility=full%20bigDataUrl=http://dkucombio.ipdisk.co.kr/publist/VOL1/Public/001_BreastCancerCell/JC1527_MCF7_PR_Progesterone_3hr-r1.CH.hg38.bigWig>`_
   

0-2. Ubuntu, Mint
^^^^^^^^^^^^^^^^^

* Commands (:download:`Quick_Start(Ubuntu,mint).txt<_templates/Quick_Start(Ubuntu,mint).txt>`):: 

   sudo apt-get install openjdk-8-jdk
   sudo apt-get install zlib1g-dev libpng12-dev libncurses5-dev build-essential r-base
   wget https://github.com/kangk1204/Octopus/archive/master.zip -O Octopus-master.zip
   unzip Octopus-master.zip
   cd Octopus-master/
   java -jar Octopus-toolkit.jar



0-3. Fedora
^^^^^^^^^^^

* Commands (:download:`Quick_Start(Fedora).txt<_templates/Quick_Start(Fedora).txt>`):: 

   sudo yum install java-1.8.0-openjdk
   sudo yum install zlib-devel.x86_64 libpng-devel.x86_64 libpng12-devel.x86_64 ncurses-devel.x86_64 gcc-c++ R
   wget https://github.com/kangk1204/Octopus/archive/master.zip -O Octopus-master.zip
   unzip Octopus-master.zip
   cd Octopus-master/
   java -jar Octopus-toolkit.jar


