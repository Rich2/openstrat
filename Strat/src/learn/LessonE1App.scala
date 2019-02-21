package learn
import ostrat._

object LessonE1App extends App
{
  var continue = false//true
  while (continue)
  {  
    val s = io.StdIn.readLine("Enter text.\n")
    deb("You said"  -- s)
    if (s == "exit") continue = false
  }
}