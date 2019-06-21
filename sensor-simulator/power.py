#importing the parent class block
from block import Block
#library used to convert python dictionaries into python
import json
#simple class to assign the power id
class Power(Block):
 power_dict= {
 	'power_id':''
 }
 #assign a json converted string to the result defined in the parent class 
 def convertJson(self):
  self.result=json.dumps(self.power_dict)
	

	
	