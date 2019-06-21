#importing the parent class block
from block import Block
#library used to convert python dictionaries into python
import json
#simple class to assign the collector id
class Collector(Block):
 col_dict= {
 	'collector_id':''
 }
 #assign a json converted string to the result defined in the parent class
 def convertJson(self):
  self.result=json.dumps(self.col_dict)
	

	