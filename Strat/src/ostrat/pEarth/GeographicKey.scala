/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth

/** Not sure about this trait. */
trait GeographicSymbolKey extends SymbolKey
{
  def name: String =
  {
    def concat(str1: String, str2: String): String =
    {
      val p1 = str1 match
      { case "" => ""
        case _ if str2 == "" => str1
        case _ if str1.last == '-' => str1
        case _ => str1 :+ ' '
      }
      
      val p2 = str2 match
      { case "N" => "North"
        case "S" => "South"
        case "W" => "West"
        case "E" => "East"
        case "C" => "Cen-"
        case "F" => "Far-"
        case "J" => "Just-"   
        case s2 => s2
      }
      p1 + p2
    }
      
    def loop(rem: List[Char], acc: String, currWord: String): String = rem match
    { case Nil => concat(acc, currWord)
      case c :: tail if c.isUpper => loop(tail, concat(acc, currWord), c.toString)
      case c :: tail => loop(tail, acc, currWord + c.toString)
    }
      
    val l = sym.name.toList
    loop(l.tail, "", l.head.toString())
  }         
}


