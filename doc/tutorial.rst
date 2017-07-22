==========
6.Tutorial
==========

A case by case tutorial.

.. csv-table::
   :header: "Tutorial ID","Description"
   :widths: 10, 35

    :ref:`6-1.Public data<tutorial1>`,How to analyze a public data with ``GEO accession number``.
    :ref:`6-2.Public data<tutorial2>`,How to analyze a list of public data with a text file containing the ``list`` of GEO accession numbers.
    :ref:`6-3.Private data<tutorial3>`,How to setup the Private table for ``user's data``.
    :ref:`6-4.Private data<tutorial4>`,How to setup the Private table when you have multiple files for a single sample: ``Multi-lane``.
    :ref:`6-5.Peak Calling<tutorial5>`,How to identified peaks using ``Peak Calling`` with the output.
    :ref:`6-6.Graph<tutorial6>`,How to draw ``Graph`` with the output.
    :ref:`6-7.IGV<tutorial7>`,How to explore genome using ``IGV`` with the output.
  
.. _tutorial1:
 
6-1.Public data (Single GSE/GSM)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. note::
    ``6-1.Public data (Single GSE/GSM)`` describes how to process publicly available data by entering a single GEO accession number. 

The way to analyze published data is very simple. Enter a GEO accession number in the input text area. Then click the Run button and Octopus-toolkit option window will appear. In the Option window, set the parameters for the analysis and click the RUN button to begin the analysis.

* ``GEO accession number`` : `GSE48685 <https://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=GSE48685>`_ (ChIP-Seq:10, RNA-Seq:1)

.. image:: _static/Tutorial/Tutorial1.1.png

* ``A`` : Enter GSE48685 in the input text area.
* ``B`` : Click the Run button
 
.. image:: _static/Tutorial/Tutorial1.2.png

* ``C`` : Select the options to analyze and click the Run button. (Option : Defalut)

Finally, Octopus-toolkit will automatically download raw files in the GSE48685 ftp directory and subsequenty analyzes the data. The output will be stored in a specified directory. No other action is required.

.. _tutorial2:

6-2.Public data (Multi GSE/GSM)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. note::
    ``6-2.Public data (Multi GSE/GSM)`` describes How to sequentially analyze a set of public data (a list of GSE accession numbers).

You may want to analyze subsamples (GSM) in a study (GSE) with several other studies (GSEs) altogether. In this case, you need to create a text file containing GSM ids for subsamples and GSE ids for studies.

An example is shown below. (:download:`example.list<_templates/GEO_Accession_number.list>`)

.. image:: _static/Tutorial/Tutorial2.1.png
   :scale: 80 %

   
Then, click the OPEN button and select the list file that you prepared.


.. image:: _static/Tutorial/Tutorial2.2.png

* ``A`` : Click the Open button
* ``B`` : Select the GEO accession number list file.
* ``C`` : Click the Open button

Then, click the RUN button. Octopus-toolkit option window will appear. In the Option window, set the parameters for the analysis and click the RUN button to begin the analysis.

.. image:: _static/Tutorial/Tutorial2.3.png

* ``D`` : Click the RUN button
* ``E`` : Select the options to analyze and click the RUN button. (Option : Defalut)

.. image:: _static/Tutorial/Tutorial2.4.png

Finally, Octopus-toolkit will automatically analyze massive data. You just have to wait for the results.

.. _tutorial3:

6-3.Private data (Basic)
^^^^^^^^^^^^^^^^^^^^^^^^

.. note::
    ``6-3.Private data (Basic)`` describes how to analyze your own data using the same analysis pipeline for the public data.

Let say you have the following data.

.. csv-table:: Analysis situation.
   :header: "NO","File name","Genome","Seq Type","SE or PE","Strand"
   :widths: 5,20,10,10,10,10 

    1,Private_ChIP-Seq_Mouse.fastq,mm10,ChIP-Seq,Single-End,Not use
    2,Private_RNA-Seq_Human_1.fastq,hg38,RNA-Seq,Paired-End,FR-Firststrand
    3,Private_RNA-Seq_Human_2.fastq,hg38,RNA-Seq,Paired-End,FR-Firststrand
    
First, open the Analysis tab and then, click Private data function.

.. image:: _static/Tutorial/Tutorial3.1.png

* ``A`` : Click the Private Data function in the Analysis menu bar.

Select your fastq files and click the Open button.

.. image:: _static/Tutorial/Tutorial3.2.png

* ``B`` : select the folder
* ``C`` : Select the files
* ``D`` : Click the Open button

The following Private Table window will appear.

.. image:: _static/Tutorial/Tutorial3.3.png
   :scale: 90 %

Case 1. let's fill in the blank for the 1.Private_ChIP-Seq_Mouse fastq file. Reads in this ChIP-seq file (single-end) should be mapped to the mm10 genome.

.. image:: _static/Tutorial/Tutorial3.4.png

* ``E`` : Select the Private_ChIP-Seq_Mouse.fastq sample.
* ``F`` : Select appropriate parameters regarding this sample. (Genome : ``mm10``, Seq-Type : ``ChIP-Seq``)
* ``G`` : Click the Insert button

Case 2. let's fill in the blank for the 2 and 3.Private_RNA-Seq_Human fastq files. Reads in this RNA-seq files (paired-end, FR-Firststrand) should be mapped to the hg38 genome. 

Octopus-toolkit automatically recognizes Paired-End files. The name of the files must be the same and ended with _1.fastq and _2.fastq

.. image:: _static/Tutorial/Tutorial3.5.png

* ``H`` : Select the Private_RNA-Seq_Human.fastq sample.
* ``I`` : Select information about this sample. (Genome : ``hg38``, Seq-Type : ``RNA-Seq``, Strand : ``FR-Firststrand``)
* ``J`` : Click the Insert button
* ``K`` : Click the Run button

The Octopus-toolkit option window will appear. In the Option window, set the parameters for the analysis and click the RUN button to begin the analysis.

.. image:: _static/Tutorial/Tutorial3.6.png

* ``L`` : Click the Run button.

.. _tutorial4:

6-4.Private data (Multi-lane)
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. note::
    ``6-4.Private data (Multi-lane)`` describes how to process your samples from multe lanes. 

.. csv-table:: Analysis situation.
   :header: "NO","File name","Genome","Seq Type","SE or PE","Strand"
   :widths: 5,20,10,10,10,10 

    1,Private_ChIP-Seq_MultiLane_L001.fastq,hg38,ChIP-Seq,Single-End,Not use
    2,Private_ChIP-Seq_MultiLane_L002.fastq,hg38,ChIP-Seq,Single-End,Not use
    3,Private_ChIP-Seq_MultiLane_L003.fastq,hg38,ChIP-Seq,Single-End,Not use

First, open the Analysis tab and then, click Private data function.

.. image:: _static/Tutorial/Tutorial4.1.png

* ``A`` : Click the Private Data in the Analysis menu bar.

Select your fastq (multi-lane) files and click the Open button.

.. image:: _static/Tutorial/Tutorial4.2.png

* ``B`` : select the folder
* ``C`` : Select the files
* ``D`` : Click the Open button

The following Private Table window will appear.

Case 1. let's fill in the blank for the Private_ChIP-Seq_MultiLane fastq file. Reads in these ChIP-seq files (single-end) should be mapped to the hg38 genome. Since all samples have the same information, you can use the all button to enter the same information at once.

.. image:: _static/Tutorial/Tutorial4.3.png

* ``E`` : Select information about this sample. (Genome : ``hg38``, Seq-Type : ``ChIP-Seq``)
* ``F`` : Click the all button
* ``G`` : Click the Insert button

Octopus-toolkit will merge the files with the same number in the Multi-Lane column prior to analysis. Please carefully assign the same number to multi-lane fastq files.

.. image:: _static/Tutorial/Tutorial4.4.png

* ``H`` : Select the Private_RNA-Seq_MultiLane Files.
* ``I`` : Select the number 1 (Multi-Lane)
* ``J`` : Click the Insert button
* ``K`` : Click the Run button

The Octopus-toolkit option window will appear. In the Option window, set the parameters for the analysis and click the RUN button to begin the analysis.

.. image:: _static/Tutorial/Tutorial4.5.png

* ``L`` : Click the Run button

.. _tutorial5:

6-5.Peak Calling
^^^^^^^^^^^^^^^^

.. note::
    ``6-5.Peak Calling`` describes how to identify peaks (enriched regions by mapped reads) with the Octopus-toolkit output.

You can identify peaks from the output: 6-1 ~ 6-4.

Let say you have the following ChIP-seq data.

.. csv-table:: Analysis situation.
   :header: "NO","Sample name","Input/Control/IgG","Style","Result Path"
   :widths: 5,10,10,10,10

    1,STAT5A_P6,Input_P6,Transcription Factor,"Result/GSE48685"

First, open the Analysis tab and then, click the Peak Calling function.

.. image:: _static/Tutorial/Tutorial5.1.png

* ``A`` : Click the Peak Calling in the Analysis menu bar.

Octopus-toolkit output will be stored in the Result folder. You need to select an appropriate study (GSE directory) in the Result folder. For example, select the GSE48685 directory.

.. image:: _static/Tutorial/Tutorial5.2.png

* ``B`` : Select the Result folder.
* ``C`` : Select the GSE48685 folder.
* ``D`` : Click the Open button.

Once you select an GSE folder (not double click), please click the Open button. Then, the Peak Calling Table will appear.

Samples of GSE48685, which were processed by Octopus-toolkit, will appear in the Sample area.

First, you need to add given samples to the Peak Calling table using the Insert function. 

.. image:: _static/Tutorial/Tutorial5.3.png

* ``E`` : Select the STAT5A_P6
* ``F`` : Click the Insert button

Then fill in the blanks for the selected samples using the Table option function. If there is a control (Control) sample to filter out background noise, you also need to add it to the Correspond column.

.. image:: _static/Tutorial/Tutorial5.4.png

* ``G`` : Select the information about STAT5A_P6 (Control : ``Input_P6``, Style : ``Transcription Factor``)
* ``H`` : Click the Insert button
* ``I`` : Click the Run button

Peak Calling analysis will be started according to the Table information.

.. image:: _static/Tutorial/Tutorial5.5.png

Once completed, you can find the result files (.bed for peaks) in the 05_Analysis directory in the Result/GSE48685 directory.

.. image:: _static/Tutorial/Tutorial5.6.png
   :scale: 90 %

* ``Result Path`` : Octopus-toolkit/Result/GSE48685

.. _tutorial6:

6-6.Graph
^^^^^^^^^

.. note::
    ``6-6.Graph`` describes how to draw plots with the output: 6-1 ~ 6-5.

You can draw heatmap and line plots with a few clicks.

6-6.Graph tutorial describes how to draw plots for multiple outputs. Let say you have the following outputs processed by Octopus-toolkit.


.. csv-table:: Analysis situation.
   :header: "NO","Sample name","Peak(.bed)"
   :widths: 5,10,10

    1,STAT5A_P6,O
    2,M_Bcl6_rep2_G50,X
    3,MH_STAT5_rep2_G41,X

* Option : +- ``1000 bp`` based on TSS, Bin Size : ``100``
   
First, open the Analysis tab and then, click the Graph function.

.. image:: _static/Tutorial/Tutorial6.1.png

* ``A`` : Click the Graph in the Analysis menu bar.

Octopus-toolkit output will be stored in the Result folder. To draw heatmap and plot, you need to select appropriate studies (GSE directories) in the Result folder. For example, select the GSE48685 and GSE31578 directories.

.. image:: _static/Tutorial/Tutorial6.2.png

* ``B`` : Select the Result folder.
* ``C`` : Select the GSE48685 and GSE31578 folders.
* ``D`` : Click the Open button.

The heatmap and plot will be drawn based on an annotation file (reference). The default annotation file (.bed) contains promoter regions. You can replace it with peak file (.bed) generated by Octopus-toolkit if you perform the peak calling analysis.

.. image:: _static/Tutorial/Tutorial6.3.png

* ``E`` : Select STAT5A_P6_CH.SE.mm10 as the reference.
* ``F`` : Select samples of your interest from the list.
* ``G`` : Click the Insert button.

In the Table option, Adjust TSS region (bp) and Number of BINs (resolution) parameters. Click the Run button to perform the Graph analysis. 

.. image:: _static/Tutorial/Tutorial6.4.png

* ``H`` : Select the 1000 in TSS region and 100 in Number of BINs 
* ``I`` : Click the Run button

Heatmap and plot will be stored in the Result/Graph folder.

.. image:: _static/Tutorial/Tutorial6.5.png

.. image:: _static/Tutorial/Tutorial6.6.png
   :scale: 90 %


.. _tutorial7:

6-7.IGV
^^^^^^^

.. note::
    ``6-7.IGV`` describes how to visualize genomes with data (bigWig files) via IGV.

Octopus-toolkit generates bigWig files which can be visuzlized using Integrative Genomics Viewer(IGV).

First, open the Analysis tab and then, click the IGV function.

.. image:: _static/Tutorial/Tutorial7.1.png

* ``A`` : Click the IGV in the Analysis menu bar.

You need to select appropriate studies (GSE directories) in the Result folder. For example, select the GSE48685 and GSE31578 directories. It will load all bigWig files in the selected directories.

.. image:: _static/Tutorial/Tutorial7.2.png

* ``B`` : Select the Result folder.
* ``C`` : Select the GSE48685 and GSE31578 folders.
* ``D`` : Click the Open button.

Let say you select the following samples. You must select an appropriate genome for visualization. Of course, you cannot load bigWig files from different genomes.

.. image:: _static/Tutorial/Tutorial7.3.png

* ``E`` : Select samples.
* ``F`` : Click the Insert button.

Click the Run button to start the Graph analysis. 

.. image:: _static/Tutorial/Tutorial7.4.png

* ``G`` : Click the Run button.

Depending on the number and size of data, it may take some time to load those files onto the IGV. Please take your time.

.. image:: _static/Tutorial/Tutorial7.5.png
   :scale: 90 %

