/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import collection.mutable.ArrayBuffer

/** An XML / HTML attribute, has a name and a value [[StrArr]]. */
trait XAtt
{ def name: String
  def valueStr: String
  def valueStrLines(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen): TextLines
  def out/*(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = MaxLineLen)*/: String = name + "=" + valueStr.enquote1
  def outLen: Int = out.length
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
  def pointStrs: StrArr = iUntilMap(numPoints){ i => arrayUnsafe(i * 2).str + "," + (-arrayUnsafe(i * 2 + 1)).str}
  override def valueStr: String = valueStrLines(0, 0, MaxLineLen).text

  override def valueStrLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines =
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
          { case "" => currLine = newStr
            case "" => currLine = indent.spaces + newStr
            case str if currLine.length + 1 + newStr.length > maxLineLen =>
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