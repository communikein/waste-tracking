#this class is used to define the common operations that are needed for every block of data
import json 
class Block:
 #this is a generic variable that store the json string conversion of a block
 result=''
 #getting and setting operations
 def getBlock():
		return self
 def setBlockType(block):
		self.blockType=block
 def getBlockType():
		return blockType
 #this method writes the converted json String on the file created in the main class
 def printOnaFile(self,text_file):  
  text_file.write(self.result)
  text_file.write(", \n")
  
  


