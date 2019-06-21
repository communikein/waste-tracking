#This class mimic the logic of a blobkchain which is made up of blocks connected to each others. Blocks, in this case are the parent class of every element that needs 
#to be converted to json values. To sum up, a blockchain is made of blocks and blocks can then be used as a generic entity for the common operations. According to the 
#necessity every child class will then have its own python dictionary with the needed values to be stored in the third party blockchain.

import block
import json
class Blockchain:
 blockList=[]
 def addBlock(self,newBlock):
  self.blockList.append(newBlock)
  print (newBlock)


 
