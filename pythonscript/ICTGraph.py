#!/usr/bin/env python
# coding: utf-8

# In[15]:


import sqlalchemy as db
import pandas as pd
import numpy as np
import networkx as nx
import matplotlib.pyplot as plt

engine = db.create_engine('mysql://root:@localhost:3308/enron_email')
connection = engine.connect()
metadata = db.MetaData()
census = db.Table('sender_receiver', metadata, autoload=True, autoload_with=engine)




query = db.select([census])




ResultProxy = connection.execute(query)





ResultSet = ResultProxy.fetchall()




df1 = pd.DataFrame(ResultSet)
del df1[0]
df1.columns = ['from', 'to']
print(len(df1['from'].unique().tolist()))
print(len(df1['to'].unique().tolist()))

df1.shape 





dfList = pd.DataFrame();
df1['from'] = df1['from'].str.strip()
dfList = df1[df1['from'].isin(['phillip.allen@enron.com',
'john.arnold@enron.com',
'harry.arora@enron.com',
'robert.badeer@enron.com',
'susan.bailey@enron.com',
'ebass@enron.com',
'don.baughman@enron.com',
'sally.beck@enron.com',
'robert.benson@enron.com',
'lynn.blair@enron.com',
'sandra.brawner@enron.com',
'rick.buy@enron.com',
'larry.campbell@enron.com',
'mike.carson@enron.com',
'michelle.cash@enron.com',
'monika.causholli@enron.com',
'shelley.corman@enron.com',
'sean.crandall@enron.com',
'martin.cuilla@enron.com',
'jeff.dasovich@enron.com',
'dana.davis@enron.com',
'clint.dean@enron.com',
'w..delainey@enron.com',
'james.derrick@enron.com',
'stacy.dickson@enron.com',
'tom.donohoe@enron.com',
'lindy.donoho@enron.com',
'chris.dorland@enron.com',
'frank.ermis@enron.com',
'j..farmer@enron.com',
'mary.fischer@enron.com',
'm..forney@enron.com',
'drew.fossum@enron.com',
'lisa.gang@enron.com',
'l..gay@enron.com',
'tracy.geaccone@enron.com',
'chris.germany@enron.com',
'doug.gilbert-smith@enron.com',
'c..giron@enron.com',
'john.griffith@enron.com',
'mike.grigsby@enron.com',
'mark.guzman@enron.com',
'e..haedicke@enron.com',
'mary.hain@enron.com',
'steven.harris@enron.com',
'rod.hayslett@enron.com',
'marie.heard@enron.com',
'scott.hendrickson@enron.com',
'juan.hernandez@enron.com',
'john.hodge@enron.com',
'keith.holst@enron.com',
'stanley.horton@enron.com',
'kevin.hyatt@enron.com',
'dan.hyvl@enron.com',
'tana.jones@enron.com',
'j.kaminski@enron.com',
'j..kean@enron.com',
'f..keavey@enron.com',
'kam.keiser@enron.com',
'jeff.king@enron.com',
'louise.kitchen@enron.com',
'tori.kuykendall@enron.com',
'lavorato@enron.com',
'kenneth.lay@enron.com',
'matthew.lenhart@enron.com',
'andrew.lewis@enron.com',
'eric.linder@enron.com',
'michelle.lokay@enron.com',
'teb.lokey@enron.com',
'm..love@enron.com',
't..lucci@enron.com',
'mike.maggi@enron.com',
'kay.mann@enron.com',
'a..martin@enron.com',
'larry.may@enron.com',
'danny.mccarty@enron.com',
'mark.mcconnell@enron.com',
'brad.mckay@enron.com',
'jonathan.mckay@enron.com',
'errol.mclaughlin@enron.com',
'steven.merris@enron.com',
'albert.meyers@enron.com',
'l..mims@enron.com',
'matt.motley@enron.com',
'scott.neal@enron.com',
'gerald.nemec@enron.com',
'stephanie.panus@enron.com',
'joe.parks@enron.com',
'w..pereira@enron.com',
'debra.perlingiere@enron.com',
'stephanie.panus@enron.com',
'vladi.pimenov@enron.com',
'phillip.platter@enron.com',
'm..presto@enron.com',
'joe.quenet@enron.com',
'dutch.quigley@enron.com',
'bill.rapp@enron.com',
'jay.reitmeyer@enron.com',
'cooper.richey@enron.com',
'andrea.ring@enron.com',
'richard.ring@enron.com',
'robin.rodrigue@enron.com',
'benjamin.rogers@enron.com',
'kevin.ruscitti@enron.com',
'elizabeth.sager@enron.com',
'eric.saibi@enron.com',
'holden.salisbury@enron.com',
'monique.sanchez@enron.com',
'b..sanders@enron.com',
'diana.scholtes@enron.com',
'darrell.schoolcraft@enron.com',
'jim.schwieger@enron.com',
'susan.scott@enron.com',
'cara.semperger@enron.com',
'sara.shackleton@enron.com',
'a..shankman@enron.com',
'richard.shapiro@enron.com',
's..shively@enron.com',
'jeff.skilling@enron.com',
'ryan.slinger@enron.com',
'matt.smith@enron.com',
'geir.solberg@enron.com',
'steven.south@enron.com',
'theresa.staab@enron.com',
'carol.clair@enron.com',
'd..steffes@enron.com',
'joe.stepenovitch@enron.com',
'chris.stokley@enron.com',
'geoff.storey@enron.com',
'j..sturm@enron.com',
'mike.swerzbin@enron.com',
'kate.symes@enron.com',
'legal <.taylor@enron.com>',
'jane.tholt@enron.com',
'd..thomas@enron.com',
'judy.townsend@enron.com',
'kim.ward@enron.com',
'barry.tycholiz@enron.com',
'charles.weldon@enron.com',
'greg.whalley@enron.com',
'w..white@enron.com',
'mark.whitt@enron.com',
'jason.williams@enron.com',
'bill.williams@enron.com',
'beth.cherry@enform.com',
'paul.y\'barbo@enron.com',
'andy.zipper@enron.com',
'john.zufferli@enron.com',
])]
print(dfList)



import re
df_temp = pd.DataFrame(columns = ['from','to'])
from IPython.display import clear_output
def convert(i):
    #Remove spaces
    i = "".join(i.split())
   #Remove next line
    i= re.sub(r"(?<=[a-z])\r?\n",'', i)
    #Remove '@'
    i = i.split("@")[0]
    #Remove "."
    i = re.sub(r'[^\w\s]',' ',i)
    #Upper case
    i = i.upper()
    return i

i1 = 0 
for index, row in dfList.iterrows():
    my_list =row['to'].split(",")
    clear_output(wait=True)
    i1 = i1 + 1
    print(i1)
    
    for i in my_list:
        
        df_temp = df_temp.append({'from': row['from'], 'to': i}, ignore_index=True)
        






df_temp['to'] = df_temp['to'].str.strip()




dfnew = df_temp[df_temp['to'].isin(['phillip.allen@enron.com',
'john.arnold@enron.com',
'harry.arora@enron.com',
'robert.badeer@enron.com',
'susan.bailey@enron.com',
'ebass@enron.com',
'don.baughman@enron.com',
'sally.beck@enron.com',
'robert.benson@enron.com',
'lynn.blair@enron.com',
'sandra.brawner@enron.com',
'rick.buy@enron.com',
'larry.campbell@enron.com',
'mike.carson@enron.com',
'michelle.cash@enron.com',
'monika.causholli@enron.com',
'shelley.corman@enron.com',
'sean.crandall@enron.com',
'martin.cuilla@enron.com',
'jeff.dasovich@enron.com',
'dana.davis@enron.com',
'clint.dean@enron.com',
'w..delainey@enron.com',
'james.derrick@enron.com',
'stacy.dickson@enron.com',
'tom.donohoe@enron.com',
'lindy.donoho@enron.com',
'chris.dorland@enron.com',
'frank.ermis@enron.com',
'j..farmer@enron.com',
'mary.fischer@enron.com',
'm..forney@enron.com',
'drew.fossum@enron.com',
'lisa.gang@enron.com',
'l..gay@enron.com',
'tracy.geaccone@enron.com',
'chris.germany@enron.com',
'doug.gilbert-smith@enron.com',
'c..giron@enron.com',
'john.griffith@enron.com',
'mike.grigsby@enron.com',
'mark.guzman@enron.com',
'e..haedicke@enron.com',
'mary.hain@enron.com',
'steven.harris@enron.com',
'rod.hayslett@enron.com',
'marie.heard@enron.com',
'scott.hendrickson@enron.com',
'juan.hernandez@enron.com',
'john.hodge@enron.com',
'keith.holst@enron.com',
'stanley.horton@enron.com',
'kevin.hyatt@enron.com',
'dan.hyvl@enron.com',
'tana.jones@enron.com',
'j.kaminski@enron.com',
'j..kean@enron.com',
'f..keavey@enron.com',
'kam.keiser@enron.com',
'jeff.king@enron.com',
'louise.kitchen@enron.com',
'tori.kuykendall@enron.com',
'lavorato@enron.com',
'kenneth.lay@enron.com',
'matthew.lenhart@enron.com',
'andrew.lewis@enron.com',
'eric.linder@enron.com',
'michelle.lokay@enron.com',
'teb.lokey@enron.com',
'm..love@enron.com',
't..lucci@enron.com',
'mike.maggi@enron.com',
'kay.mann@enron.com',
'a..martin@enron.com',
'larry.may@enron.com',
'danny.mccarty@enron.com',
'mark.mcconnell@enron.com',
'brad.mckay@enron.com',
'jonathan.mckay@enron.com',
'errol.mclaughlin@enron.com',
'steven.merris@enron.com',
'albert.meyers@enron.com',
'l..mims@enron.com',
'matt.motley@enron.com',
'scott.neal@enron.com',
'gerald.nemec@enron.com',
'stephanie.panus@enron.com',
'joe.parks@enron.com',
'w..pereira@enron.com',
'debra.perlingiere@enron.com',
'stephanie.panus@enron.com',
'vladi.pimenov@enron.com',
'phillip.platter@enron.com',
'm..presto@enron.com',
'joe.quenet@enron.com',
'dutch.quigley@enron.com',
'bill.rapp@enron.com',
'jay.reitmeyer@enron.com',
'cooper.richey@enron.com',
'andrea.ring@enron.com',
'richard.ring@enron.com',
'robin.rodrigue@enron.com',
'benjamin.rogers@enron.com',
'kevin.ruscitti@enron.com',
'elizabeth.sager@enron.com',
'eric.saibi@enron.com',
'holden.salisbury@enron.com',
'monique.sanchez@enron.com',
'b..sanders@enron.com',
'diana.scholtes@enron.com',
'darrell.schoolcraft@enron.com',
'jim.schwieger@enron.com',
'susan.scott@enron.com',
'cara.semperger@enron.com',
'sara.shackleton@enron.com',
'a..shankman@enron.com',
'richard.shapiro@enron.com',
's..shively@enron.com',
'jeff.skilling@enron.com',
'ryan.slinger@enron.com',
'matt.smith@enron.com',
'geir.solberg@enron.com',
'steven.south@enron.com',
'theresa.staab@enron.com',
'carol.clair@enron.com',
'd..steffes@enron.com',
'joe.stepenovitch@enron.com',
'chris.stokley@enron.com',
'geoff.storey@enron.com',
'j..sturm@enron.com',
'mike.swerzbin@enron.com',
'kate.symes@enron.com',
'legal <.taylor@enron.com>',
'jane.tholt@enron.com',
'd..thomas@enron.com',
'judy.townsend@enron.com',
'kim.ward@enron.com',
'barry.tycholiz@enron.com',
'charles.weldon@enron.com',
'greg.whalley@enron.com',
'w..white@enron.com',
'mark.whitt@enron.com',
'jason.williams@enron.com',
'bill.williams@enron.com',
'beth.cherry@enform.com',
'paul.y\'barbo@enron.com',
'andy.zipper@enron.com',
'john.zufferli@enron.com',
])]

print(dfnew)





# Build your graph
import math 

G = nx.from_pandas_edgelist(dfnew, 'from', 'to')

default_weight = 0.01

for index, row in dfnew.iterrows():
    n0 = row['from']
    n1 = row['to']
    G.add_edge(n0,n1, weight=0)


for index, row in dfnew.iterrows():
    n0 = row['from']
    n1 = row['to']
    if G.has_edge(n0,n1): 
       G[n0][n1]['weight'] += default_weight
       #print(G[n0][n1]['weight'])
    else:
       G.add_edge(n0,n1, weight=default_weight)
       

print(G.number_of_nodes())
    
pos = nx.spring_layout(G,k=0.3*1/np.sqrt(len(G.nodes())),iterations=1)
plt.figure(3, figsize=(30, 30))
edges = G.edges()
weights = [G[u][v]['weight'] for u,v in edges]
# Plot it
nx.draw(G, pos ,with_labels=True,
        edge_cmap=plt.cm.Blues,
        
        # node customizations
        node_size=100,
        node_color="red",
        node_shape="o",
        linewidths=10,
        alpha=0.7,
        # label customization
        font_size=10,
        font_color="#000000",
        font_weight="bold",
        
        # edge customization
        width=weights,
        edge_color="orange",
        style="solid")


nx.write_gexf(G, "test.gexf")
plt.savefig('test.png')
plt.show()

