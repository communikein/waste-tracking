#importing the parent class block
from block import Block
#library used to convert python dictionaries into python
import json
#simple class to assign the treatment plant id
class Treatment_Plant(Block):
 treat_dict= {
 	'treatment_plant_id':''
 }
 #assign a json converted string to the result defined in the parent class
 def convertJson(self):
  self.result=json.dumps(self.treat_dict)
	

	