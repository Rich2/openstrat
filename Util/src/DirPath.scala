/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

trait DirPath
{ /** The path as a string with the slash characters inserted */
  def asStr: String

  def noExistStr: String = asStr -- "Doesn't exist"

  def notDirStr: String = asStr -- "is not a directory"

  def /> (appendStr: String): String
}

object DirPath
{
  def strToStrs(inp: String): Array[String] =
  { val res0 = inp.dropWhile(_.isWhitespace).dropWhile(!_.isLetter).dropRightWhile(_.isWhitespace).dropRightWhile(_ == '/')
    val acc: ArrayBuffer[String] = Buffer[String]()
    def loop(rem: String): Unit = if (rem.length == 0) {}
    else{
      val newStr = rem.takeWhile(_ != '/')
      acc.append(newStr)
      val rem2 = rem.drop(newStr.length)
      if(rem2.length == 0){}
      else loop(rem2.dropWhile(!_.isLetter))
    }
    acc.toArray
  }
}

/** Directory path absolute. */
class DirPathAbs(val arrayUnsafe: Array[String]) extends DirPath
{
  override def asStr: String = ife(arrayUnsafe.length == 0, "/", arrayUnsafe.foldLeft("")(_ + "/" + _))

  override def toString: String = "DirPathAbs" + asStr.enParenth

  def / (newDir: DirPathRel): DirPathAbs = DirPathAbs(arrayUnsafe ++ newDir.arrayUnsafe)

  def /> (appendStr: String): String = asStr / appendStr

  def /< (appendStr: String): DirPathAbs = {
    val newArray: Array[String] = new Array[String](arrayUnsafe.length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(arrayUnsafe.length) = appendStr
    new DirPathAbs(newArray)
  }
}

object DirPathAbs
{
  implicit val showEv: Show[DirPathAbs] = new Show[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"
    override def strT(obj: DirPathAbs): String = obj.asStr
    override def syntaxDepth(obj: DirPathAbs): Int = 1
    override def show(obj: DirPathAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
      case ShowTyped | ShowStdTypedFields => typeStr + obj.asStr.enParenth
      case _ => obj.asStr
    }
  }

  implicit val unshowEv:Unshow[DirPathAbs] = new Unshow[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"

    override def fromExpr(expr: Expr): ExcMon[DirPathAbs] =  expr match
    { case SlashToken(_) => Succ(new DirPathAbs(Array[String]()))
      case PathToken(_, array) => Succ(new DirPathAbs(array))
      case expr => expr.failExc("Not an absolute path")
    }
  }
}

/** Directory path absolute. */
class DirPathRel(val arrayUnsafe: Array[String]) extends DirPath
{
  override def asStr: String = arrayUnsafe.length match
  { case 0 => ""
    case 1 => arrayUnsafe(0)
    case _ => arrayUnsafe.mkString("/")
  }

  override def toString: String = "DirPathRel" + asStr.enParenth

  def /(newDir: String): DirPathRel ={
    new DirPathRel(arrayUnsafe.appended(newDir))
  }

  def /> (appendStr: String): String = ife(arrayUnsafe.length == 0, asStr, asStr / appendStr)
  
  /** Not fully implemented. */
  def </(operand: DirPathRel): DirPathRel = arrayUnsafe.length match
  {
    case 0 => operand
    case _ =>
    { val array = new Array[String](operand.arrayUnsafe.length + 1)
      array(0) = ".."
      operand.arrayUnsafe.copyToArray(array, 1)
      new DirPathRel(array)
    }
  }
}

object DirPathRel
{
  def apply(inp: String*): DirPathRel = new DirPathRel(inp.toArray)
}