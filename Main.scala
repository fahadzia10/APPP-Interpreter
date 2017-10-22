import scala.io.Source
import scala.util.matching.Regex
import scala.reflect.runtime.universe
import scala.tools.reflect.ToolBox

object Main {

  def main(args:Array[String]):Unit={   
    //Make toolbox to run dynamic scala code as soon as its translated
    val tb = universe.runtimeMirror(getClass.getClassLoader).mkToolBox()        
    //Read File
    val file=Source.fromFile("program2.appp")
    //Get each line and call function for each to break it into expressions
    for(line<-file.getLines){
      //Check For Declaration
      if(checkDeclaration(line)){
        getDeclarationPattern.findAllIn(line).matchData foreach {
           m => //tb.eval(tb.parse(writeDeclaration(m.group(1), m.group(2))))
           printf("Translated: %s", writeDeclaration(m.group(1), m.group(2)))
           println("\nDone!\n")
        }  
      }
      else{
        //Check for a new assignment
        if(checkNewAssignment(line)){
          getNewAssignmentPattern.findAllIn(line).matchData foreach {
            m => //tb.eval(tb.parse(writeNewAssignment(m.group(1), m.group(2), m.group(3))))
            printf("Translated: %s", writeNewAssignment(m.group(1), m.group(2), m.group(3)))
            println("\nDone!\n")
          }
        }
        else{
          //Check for an assignment of a declared variable
          if(checkAssignment(line)){
            getAssignmentPattern.findAllIn(line).matchData foreach {
              m => //tb.eval(tb.parse(writeAssignment(m.group(1), m.group(2))))
              printf("Translated: %s", writeAssignment(m.group(1), m.group(2)))
              println("\nDone!\n")
            }
          }
          else{
            //Check for while loop
            if(checkWhile(line)){
              getWhilePattern.findAllIn(line).matchData foreach {
                m => writeWhile(m.group(3), m.group(7))
                println("Done!\n")
              }
            }
            else{
              println("Thats all I can do for now!")
            }            
          }          
        }
      }
    }
    //Close file
    file.close()
  }
  
  def getDeclarationPattern():Regex={
      val pattern = new Regex("var\\s*(\\w*\\d*):(int|bool|alpha)\\s*(\\n*|;)")
      pattern
  }     
  def checkDeclaration(a:String):Boolean={
    //If it matches it, true
    if(a.matches(getDeclarationPattern.toString())){
     println("Yes! Its a declaration")
     true
    }  
    //Else return false, not a declaration
    else{
      false
    }    
  }
  def writeDeclaration(Name:String,Type:String):String={
    Type match {
       case "int" => {val command = s"var $Name:Int"; command}
       case "bool" => {val command = s"var $Name:Boolean"; command}
       case "alpha" => {val command = s"var $Name:String"; command}
     }    
  }
  
  def getNewAssignmentPattern():Regex={
    val pattern = new Regex("var\\s*(\\w*\\d*):(int|bool|alpha)\\s*=\\s*(.*)(\\n*|;)")
    pattern
  }
  def checkNewAssignment(a:String):Boolean={
    //If it matches it, true
    if(a.matches(getNewAssignmentPattern.toString())){
     println("Yes! Its a new assignment")     
      true
    }  
    //Else return false, not new assignment
    else{
      false
    }    
  } 
  def writeNewAssignment(Name:String,Type:String, value:String):String={
    Type match {
       case "int" => {val command = s"var $Name:Int = $value"; command}
       case "bool" => {val command = s"var $Name:Boolean = $value"; command}
       case "alpha" => {val command = s"var $Name:String = $value"; command}
     }
  }
  
  def getAssignmentPattern():Regex={
    val pattern = new Regex("\\s*(\\w*\\d*)\\s*=\\s*(\\w*\\d*|\\d*)(\\n*|;)")
    pattern
  }
  def checkAssignment(a:String):Boolean={
    //If it matches it, true
    if(a.matches(getAssignmentPattern.toString())){
     println("Yes! Its an assignment!")
      true
    }  
    //Else false, not an assignment   
    else{
      false
    }    
  }
  def writeAssignment(Name:String, value:String):String={
    val command = s"$Name = $value;"; 
    command
  }
  
  def getAdditionPattern():Regex={
    val pattern = new Regex("\\s*(\\w*\\d*|\\d*)\\s*+\\s*(\\w*\\d*|\\d*).")
    pattern
  }
  def checkAddition(a:String):Boolean={
    //If it matches it, true
    if(a.matches(getAdditionPattern.toString())){
     println("Yes! Its an addition!")
      true
    }  
    //Else false, not an addition    
    else{
      false
    }    
  }
  def writeAddition(First:String, Second:String):String={
    val command = s"$First + $Second"
    command
  }  
   
  def getWhilePattern():Regex={
    val pattern = new Regex("\\s*((W|w)hile)\\s*((\\w*\\d*|\\d*|tt|ff)(<|>|==)(\\w*\\d*|\\d*|tt|ff))\\s*do\\s*(.*)")
    pattern
  }
  def checkWhile(a:String):Boolean={
    //If it matches it, true
    if(a.matches(getWhilePattern.toString())){
     println("Yes! Its a while loop!")
      true
    }  
    //Else false, not a while loop    
    else{
      false
    }    
  }
  def writeWhile(condition:String, toDo:String):Unit={

    val tb = universe.runtimeMirror(getClass.getClassLoader).mkToolBox()    
    if(checkAssignment(toDo)){
        getAssignmentPattern.findAllIn(toDo).matchData foreach {
        m => val translatedToDo = writeAssignment(m.group(1), m.group(2));
        val command = s"while($condition){\n$translatedToDo\n}";
        printf("Translated: %s", command)
        //tb.eval(tb.parse(command))
      }  
    }
  }  
} 