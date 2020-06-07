#!/usr/bin/env python
# coding: utf-8




from datetime import datetime


import sqlalchemy as db
import pandas as pd
import numpy as np
import networkx as nx
import matplotlib.pyplot as plt

import mysql.connector
from scipy import sparse
from sklearn.metrics.pairwise import cosine_similarity

from sqlalchemy import create_engine


import pymysql

from wordcloud import WordCloud



sqlEngine       = create_engine('mysql+pymysql://root:@127.0.0.1', pool_recycle=3600)

dbConnection    = sqlEngine.connect()

ResultSet           = pd.read_sql("SELECT * FROM enron_email.nischal ", dbConnection);

pd.set_option('display.expand_frame_repr', False)



dbConnection.close()

ResultSet.shape



rs=ResultSet[:68]




print(rs.head())





import seaborn as sns
sns.set_style('whitegrid')
get_ipython().run_line_magic('matplotlib', 'inline')
import matplotlib.pyplot as plt    




x=rs['Year_Month']
y=rs['Total_Email']

import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns


plt.figure(figsize = (16,9)) # figure size with ratio 16:9
sns.set(style='darkgrid',) # background darkgrid style of graph 
 
plt.title("Enron Email Statistics 1979-2012", fontsize = 20)

sns.lineplot(x="Year_Month", y="Total_Email",ci=None, data=rs)

plt.xlabel("Year", fontsize = 15)
plt.ylabel("Number of mail", fontsize = 15)


plt.xticks(rotation=90)
plt.show()

