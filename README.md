# Domain Spesific Question Answering Assistant


## **Abstract**

The goal of this project is to implement a text based question answering system which
can  be  used  for  different  sectors.   The  system  was  designed  that  finds  the  closest
questions among the previously asked questions by user and also the system returns
correct answer to the user.   In case of the answer could not be found,  this system
informs the user and finds soon a suitable answer. If the chosen answer is satisfactory
for the user, the new question-answer binary is added to the database. After a certain
period of time, the data is retrained to increase the success of system.

To find correct answer the closest question among the previously asked questions need
to be found. This problem was solved with two different approaches. First approach is
using neural network and second approach is using string similarity methods( Cosine
similarity, Levenhstein distance, Qgram similarity) as a solution of the problem.

Neural networks models depend on size of data significantly, therefore string similarity
methods can be used in some circumstances which insufficient sample exist in dataset.
On the other hand, in case of the size of data increases, the accuracy of the neural
network model will also increase.

**Apk file of mobile application: https://github.com/umutguneri/Question-answering-assistant/blob/master/mobileAssistant/mobileAssistant.apk**

**Jar file of desktop application: https://github.com/umutguneri/Question-answering-assistant/blob/master/Desktop%20Application%20(Java%20GUI)/Chatbot%20Assistant/Assistant.jar**

**Literature Review**

On this project, it is aimed to response with appropriate answers to the questions asked
by the user.  It is expected that the system will only answer the questions in specific
sector because this question answering system will be prepared for a specific sector
purpose.

**Objective of the Thesis**

The project is a question answering system that improves learning with the logic that
is set up at the back. It is aimed to create more accurate and meaningful answers over
time. Methods to be used;
1.  Determining the needs and deficiencies in the first stage and eliminating the
data and other needs for the project,
2.  Collection of question and answer text data from specific sectors,
3.  Cleaning of collected data,
4.  Training  of  different  approaches  of  gathered  data  deep  learning  framework,
application of optimization, regulation methods,
5.  Testing various methods to find similar questions to the question asked,
6.  If there are no similar questions, asking the user for new questions and getting
the correct answer,
7.  If the system fails, re-arrange, train and test the new model.

**Hypothesis**

On this project my purpose is to give correct answers to the questions asked by users,
although the level of achievement can not be reached in the first stage, it is aimed to
reaching the acceptable levels of this level step by step.

**System Requirements**

- The web interface requires the following dependencies:
- python 3.5
- tensorflow (tested with v1.0)
- numpy
- CUDA (for using GPU)
- nltk (natural language toolkit for tokenized the sentences)
- tqdm (for the nice progression bars)

For The web interface requires these packages:
–django
–channels
–Redis
–asgi-redis

Workflow Schema for question answering system is shown in Figure:

image-1

## Dataset

The dataset which is required for implementation of my project, has been obtained
from section of frequently asked questions in Ziraat Bank website
. On this section of  website  there  were  approximately  400  pair  of  question-answer 
about  different subjects in banking sector.

This  size  of  dataset  is  not  enough  for  implementation  my  deep  learning  model.
However the dataset can be used for beginning. Using desktop application this dataset
can be enlarged for the deep learning model

Some of sample questions and answers are shown in Figure:

image-2

## Implementation of Model on Web Interface

The models in this work are implemented with Tensorflow, and all experiments
are processed in a GPU cluster. I use the accuracy on validation set to locate the best
epoch and best hyper-parameter settings for testing.

The word embedding is trained by Word2vec, and the word vector size is 300.
Word embeddings are also parameters and are optimized as well during the training.
Stochastic Gradient Descent(SGD) is the optimization strategy.  I tried different
margin values, such as 0.05, 0.1 and 0.2, and finally fixed the margin as 0.2.
As  an  network  architecture,  multi-layer  perceptron  model  has  been  used  on  web
interface.  This model has 5 layer in total.  (1 of them as an Input Layer, 3 of them
as an Hidden Layer, 1 of them as an output Layer).

Each word vector, which is obtained of the questions in the data set, is used as input.
The output layer has the indexes of the sentences placed in order. During training of
the model after every epoch, the sentence index in the output layer is changed to 1 as
14 a binary value. Total number of questions in dataset is currently 488, which are used
for training the model.

After 100 epochs the training phase of model is ended.  Leaning rate is set to 0.001
and dropout rate is set to 0.5.  This approach is very similar to the text classification
methods, which use artificial neural networks.

Multi-layer perceptron model for question answering is shown in Figure:

image-3

## Implementation of Desktop Application

The  size  of  dataset  is  not  enough  for  implementation  my  deep  learning  model.
Therefore, in some cases the web application can not reply with correct answer.
The solution for this problem is enlarging dataset for the deep learning model using
desktop application.  The mentality of desktop application is not complicated.  The
program finds closest question to obtained question using some similarity methods
such as cosine similarity, levenshtein distance and Qqram similarity. If the user is 
satisfied with answer then he has to click Ok button so the new question and answer 
will be added on database.

If the user is not satisfied with answer,  the user can choose one of 5 most similar
questions. In this case, a new answer will be shown on text area. If the user is still not
satisfied with answer, then he has to click on Not Ok button, the question and answer
will be added in another database.  The admin will enter new answers after a while
for the question.

Using this desktop application, enlarging dataset for the deep learning model can be
possible.

UML diagram of desktop application is shown in Figure:

image-4

## Screenshots of Web Application

The main page of Web application is shown in Figure.  For now it is only for a
demo homepage. Users can write their own question in text label as an input. After a
couple of milliseconds the system will print the answer to the screen:

image-5

User can ask a question using web application. It is shown in Figure:

image-6

The answer and question will be shown on text area. It is shown in Figure:

image-7

If the user change previously asked question and asks a new question, the new answer
will be shown on text area. It is shown in Figure:

image-8

## Screenshots of Desktop Application

The main page of desktop application is shown in Figure .  Users can write their
own question in text label as an input.  After a couple of milliseconds the system will
print the answer to the screen:

image-9

User can ask a question using desktop application.  For this sample question :"Ziraat
internet  bankaciligina  hangi  adreslerden  ulasabilirim?"  the  answer,  most  similar
question  and  similarity  rate  are  shown  on  screen.   Due  the  question  is  already  in
database, similarity rate is 1.0 and correct answer is seen in text area.  It is shown
in Figure:

image-10

If the user change just one word on previously asked question and asks this question:
"Ziraat internet bankaciligina hangi adreslerden ulasabilirim?", similarity rate will be
0.9 and same answer will be seen on text area. It is shown in Figure:

image-11

At the bottom of text area an important question is asked to user.  It means "Are you
satisfied with answer?".  Also, Ok and Not ok button take part in right side of this
question. It is shown in Figure:

image-12

If the user click on Ok button, the question and answer will be added in database. The
message "The question is added in database" is shown in Figure:

image-13

if the user click on Not Ok button, the question and answer will be added in another
database.   The admin will enter new answers after a while for the question.   The
message "For your question will be found better answer in soon" is shown in Figure:

image-14

If  the  user  change  the  question  more  deeply,  the  similarity  rate  between  most
similar  question  will  drop.   For  this  example  question  :"Ziraat  bankaciligina  nasil
ulasabilirim?" similarity rate will be 0.7 and correct answer will be able to found by
application. It is shown in Figure:

image-15

In this application, the user can choose the method which is used to find most similar
question and its answer. For a now possible methods are cosine similarity, levenshtein
and qram. It is shown in Figure:

image-16

If the user is not satisfied with question, the user can choose one of 5 most similar
questions.  In this case, a new answer will be shown on text area.  Possible questions
section is shown in Figure:

image-17

## Screenshots of Mobile Application

Before the main page of mobile application is shown, the splash screen is shown during
a couple of seconds on android application. It is shown in Figure:

image-18

The main page of mobile application is shown in Figure.  Users can write their
own question in text label as an input.  After a couple of milliseconds the system will
print the answer to the screen:

image-19

User can ask a question using mobile application.  For this sample question :"Kartım
kayboldu, ne yapabilirim?" the answer, most similar question and similarity rate are
shown on screen. It is shown in Figure:

image-20

At the above of text area an important question is asked to user.  It means "Are you
satisfied with answer?".  Also, Ok and Not ok button take part in right side of this
question. It is shown in Figure:

image-21

If the user click on Ok button, the question and answer will be added in database. The
message "The question is added in database" is shown in Figure:

image-22

If the user click on Not Ok button, the question and answer will be added in another
database.   The admin will enter new answers after a while for the question.   The
message "For your question will be found better answer in soon" is shown in Figure:

image-23

If the user is not satisfied with question, the user can choose one of 5 most similar
questions.  In this case, a new answer will be shown on text area.  Possible questions
section is shown in Figure:

image-24

## Experimental Results 

An  evaluation  model  was  used  to  measure  user  satisfaction  with  the  question
answering  assistants.   10  different  participants  were  given  hard  copy  form  of  50
question samples in the dataset to use the question-answer assistant.

Participants were requested to ask similar or related questions to these questions using
web application and desktop application.  After each response of the application, the
users evaluated the system with a score of 1-5.   This evaluating was based on the
following 4 criteria:

1.  Were the answers given by Question Answering Assistant correct? (Quality)
2.  Are you satisfied with answers? (Quantity)
3.  Were the answers given by Question Answering Assistant relevant to subject? (Relation)
4.  Were the answers given by Question Answering Assistant clear? (Manner)

The result of the rating is shown in Table, which have done by the participants:

image-25

According to this frequency values accuracy can be calculated using sample mean
formula.

image-26

Accordingly, the overall success of the system was estimated to be approximately **92%.

**Performance Results

Question Answering Assistant has been tested on two different platforms (Windows
and  Android).   This  test  was  examined  according  to  criteria  such  as  time,  speed,
success.

First, when the application created on the mobile platform is examined, the opening
time of the program takes 1-2 ms. No stuck or waiting period occurs during usage.
Similarly, when we look at the desktop and web application, the opening time of the
program lasts in ms. There is no hanging or waiting during usage.

## Conclusion

In this project, a text based question answering assistant was implemented which can
be used for different sectors. The system was designed that finds the closest question
among the previously asked questions by user and also the system returns correct
answer to the user. In case of the answer could not be found, this system informs the
user and finds soon a suitable answer. If the chosen answer is satisfactory for the user,
the new question-answer binary is added to the database.  After a certain period of
time, the data is retrained to increase the success of system.

Two  different  methods  has  been  used  to  find  best  answer.   First  method  is  to  use
neural network model (LSTM) and second method is to use string similarity methods(
Cosine similarity, Levenhstein distance, Qgram similarity) to find appropriate answer.
Although both methods are considered successful enough, the neural network model
needs more data sets.

When the experimental results of the project were reviewed, it was found that the
participants evaluated the system very successfully.  In 4 different criteria (Quality,
Quantity, Manner, Relation) it was scored over 4 out of 5. Overall score is 4.6 points,
which is about 92%.

On this project my purpose was to give correct answers to the questions asked by
users, although the level of achievement could not be reached in the first stage, it was
reached the acceptable levels of this level step by step.  There are not many studies
done in Turkish about question and answer systems.  I hope this work will be useful
for other studies.

## References









