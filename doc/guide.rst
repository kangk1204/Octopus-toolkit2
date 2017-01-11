==========
User Guide
==========

Octopus-toolkit can analyze data already published on NCBI or stored on your computer.

* ``Public data`` : The NGS data uploaded to NCBI.
* ``Private data`` : The NGS data stored on the user's computer. (Data obtained through a sequencing machine or downloaded directly in the web)

Also, Octopus-toolkit can analyze further analysis such as ``Peak Calling``, ``Drawing Graph``, and ``Visualization`` after analyzing the public data or private data.

The basic analysis of Octopus-toolkit uses public data.

* ``NGS data processing``

.. image:: _static/Guide/0.NGS_data_processing.png

The analysis using Octopus-toolkit is based on the process from preprocessing to visualization. You can analyze the further analysis after the completion of the visualization process. 

Public data
^^^^^^^^^^^

.. image:: _static/Guide/1.Public_Data_Input.png

To analyze the public data, enter the GEO (Gene Expression Omnibus) Accession number or load the GEO Accession number list file.

* Input : ``GEO Accession number`` ::

   GSExxx : Each Series(Study) record is assigned a unique and stable GEO accession number.
   GSMxxx : Each Sample record is assigned a unique and stable GEO accession number.

* Input : ``GEO Accession number list`` (:download:`example.list<_templates/GEO_Accession_number.list>`)

1. Enter the GEO accession number or click the open button to load GEO accession number list.
2. Click the Run button.

You can use the options provided by Octopus-toolkit as well as the tools used by each process.

.. image:: _static/Guide/2.Octopus_Option.png

.. csv-table::
   :header: "Option","Discription"
   :widths: 10,35

   ``Latest genome version``, Use the latest genome.
   ``Analyze the data in succession``, Skips already analyzed the samples.
   ``Omit Process``,Omit the selected process. (Trimming and Sort)
   ``CPU(Thread)``,Set the CPU to use.
   ``Strand(RNA)``,Set the library strand for RNA-Seq.
   ``Use the full parameter for each tool``,Use the full parameter.
   ``Edit Button``,Go to the Full parameter option window.
   ``Remove Files``,Delete selected files after each process analysis for HDD free space.

* ``Latest genome version``

Octopus-toolkit can analyze for Homo sapiens, Mus musculus, Drosophila melanogaster, Saccharomyces cerevisiae, and Canis lupus familaris.

.. list-table:: Available analysis genome version
   :widths: 10 10
   :header-rows: 1

   * - Organism
     - Genome version
   * - Homo sapiens
     - hg38, hg19, hg18
   * - Mus musculus
     - mm10, mm9
   * - Drosophila melanogaster
     - dm6, dm3
   * - Saccharomyces cerevisiae
     - sacCer3
   * - Canis lupus familaris
     - canFam3

The latest genome version uses the latest version of the genome for analysis. If you don't select this option, Octopus-toolkit uses the following genome version for analysis. ::

 * Latest genome (O) : hg38, mm10, dm6, sacCer3, canFam3
 * Latest genome (X) : hg19, mm9, dm3, sacCer3, canFam3

* ``Analyze the data in succession``

When analyzing a GSE accession number that has a lot of sample data, you may have to shut down the computer during analysis for a variety of reasons. For example, if you stop analyzing after 8 samples of 10 samples have been analyzed, and later analyze again, you will have to analyze again from the beginning.(First sample) 

To solve this issue, Octopus-toolkit saves analysis information of the sample in the log file after the analysis of one sample is completed.

If you select analyze the data in succession, you can skip the already analyzed samples.

If you have already analyzed the sample, but you want to reanalyze it with other options, Don't select analyze the data in succession.

* ``Omit process``

The omit process allows you to skip and analyze the selected process during the entire analysis process. You can shorten the overall analysis time by omitting these processes.

In the trimming process, If all reads have bad quality, there is a possibility that all reads are deleted. Octopus-toolkit will analyze the original raw data(Fastq) when all reads are deleted due to bad quality after trimming.

Also, If all reads are higher than the trimming cutoff (quality, adapt), you can skip to the 
trimming process.

In the sorting process, The alignment process in Octopus-toolkit provides you with a bam format file and a sorted.bam file that sorts it.

Sorted.bam files can't be used for visualization , peak calling, and graph function after alignment process. It is used only when confirming the bam file directly via IGV tool.
(The IGV process basically uses the bigWig format file, which is the output file of Visualization)

However, the reason for creating sorted.bam file in Octopus-toolkit is that many NGS tools use the sorted.bam file as input for further analysis.

So, If you don't need a sorted.bam file you can skip this sorting process to save time.

* ``CPU(Thread)``

Octopus-toolkit can select the CPU to your computer for analysis. (Default : 8)

* ``Strand(RNA)``

Strand is the library's strand information needed to analyze RNA-Seq data.

Octopus-toolkit extracts information from samples in the GEO dataset when analyzing public data. However, information about strand is not well provided in GEO datasets.

So, when you want to analyze the RNA-Seq data, you can set the strand specific library or non-strand library via this option. (Default : Unstrand)

You can select either non-strand library or the strand-specific library such as FR-Firststrand, FR-Secondstrand using this option.

* ``Use the full parameter for each tool``

Use the full parameter for each tool (Checkbox) allows you to select the full option of the tool used in each analysis.

When you select it, the Edit button is enabled. You can go to the Full parameter option window via the Edit button.

* ``Edit Button``

The Edit button will be active when you select Use the full parameter for each tool. If you click the Edit button, the Full parameter optinos window will appear.

* ``Remove Files``



Private data
^^^^^^^^^^^^

Peak Calling
^^^^^^^^^^^^

Graph
^^^^^

Visualization
^^^^^^^^^^^^^
