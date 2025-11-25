/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import collection.mutable.ArrayBuffer

/** An XML / HTML attribute, has a name and a value [[StrArr]]. */
trait XAtt
{ /** Name of this attribute. Not to be confused with the name of its parent element. */
  def name: String

  /** The combined String from valueOutLines. */
  def valueOut(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String = valueOutLines(indent + 2, line1InputLen, maxLineLen).text

  /** Returns the text lines for the value of this attribute. */
  protected def valueOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines

  def out: String = out(0, 0, MaxLineLen)
  
  def out(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): String =
    name + "=" + valueOutLines(indent + 2, line1InputLen + 2 + name.length, MaxLineLen).text.enquote1

   def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines = {
     val value = valueOutLines(indent + 2, line1InputLen + 2 + name.length, MaxLineLen)
     value.numLines match{
       case 0 => TextLines.empty
       case 1 => TextLines(name + "=" + value.lines(0))
       case n => {
         val newArray = new Array[String](n)
         newArray(0) = name + "=" + value.lines(0)
         iUntilForeach(1, n){i => newArray(i) = value.lines(0)}
         new TextLines(newArray)
       }
     }
   }
}

/** Companion object for the XML attribute [[XAtt]] trait. */
object XAtt
{ /** Factory apply method for [[XAtt]] trait. Often you may prefer to use the subclasses of [[XAtt]] where the name of the attribute has already been set. */
  def apply(name: String, valueStr: String): XAtt = XmlAttGen(name, valueStr)
}

/** An SVG / XML / HTML points attribute. */
class PointsAtt(val arrayUnsafe: Array[Double]) extends XAtt
{ override def name: String = "points"
  def numPoints = arrayUnsafe.length / 2
  def pointStrs: StrArr = iUntilMap(numPoints){ i => arrayUnsafe(i * 2).str2 + "," + (-arrayUnsafe(i * 2 + 1)).str2}

  override def valueOutLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
  { val pStrs = pointStrs
    numPoints match
    { case 0 => TextLines.empty
      case 1 => TextLines(pointStrs(0))
      case _ =>
      { var i = 0
        val res = new ArrayBuffer[String]
        var currLine = ""
        while (i < numPoints)
        { val newStr: String = pStrs(i)
          currLine match
          { case "" if res.length == 0 => currLine = newStr
            case "" => currLine = indent.spaces + newStr

            case str if (res.length == 0 && line1InputLen + currLine.length + 1 + newStr.length > maxLineLen) ||
              (currLine.length + 1 + newStr.length > maxLineLen) =>
            { res.append(currLine)
              currLine = indent.spaces + newStr
            }
            case str => currLine = currLine + " " + newStr
          }
          i += 1
        }
        ifExcep(currLine == "", "Unexpected emoty String.")
        res.append(currLine)
        new TextLines(res.toArray)
      }
    }
  }
}