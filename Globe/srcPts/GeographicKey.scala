/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth

/** Not sure about this trait. */
trait GeographicSymbolKey
{ /** The Shortened name for this geographic area. */
  def name: String

  /** The name of the is Geographic Area. */
  /*def name: String =
  {
    def concat(str1: String, str2: String): String =
    { val s1 = str1 match
      { case "" => ""
        case _ if str2 == "" => str1
        case _ if str1.last == '-' => str1
        case _ => str1 :+ ' '
      }
      val s2 = str2 match
      { case "N" => "North"
        case "S" => "South"
        case "W" => "West"
        case "E" => "East"
        case "C" => "Central"
        case "F" => "Far-"
        case "J" => "Just-"
        case s2 => s2
      }
      s1 + s2
    }*/

    /*def loop(rem: List[Char], acc: String, currWord: String): String = rem match
    { case Nil => concat(acc, currWord)
      case c :: tail if c.isUpper => loop(tail, concat(acc, currWord), c.toString)
      case c :: tail => loop(tail, acc, currWord + c.toString)
    }

    val list = shortName.toList
    loop(list.tail, "", list.head.toString())
  }         */
}