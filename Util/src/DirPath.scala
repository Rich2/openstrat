/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse.*, collection.mutable.ArrayBuffer, annotation.*

trait DirPath
{ /** The path as a string with the slash characters inserted */
  def asStr: String

  /** A notification [[String]] to inform that the path doesn't exist. */
  def noExistStr: String = asStr -- "Doesn't exist"

  /** A notification [[String]] to inform that the path is not a directory. */
  def notDirStr: String = asStr -- "is not a directory"

  /** Appends a [[String]] and converts the path to a [[String]] */
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
    loop(res0)
    acc.toArray
  }
}

/** Directory path absolute. */
class DirPathAbs(val arrayUnsafe: Array[String]) extends DirPath
{ /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(newDir: DirPathRel): DirPathAbs = new DirPathAbs(arrayUnsafe ++ newDir.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirPathRel]] */
  @targetName("append") def /(operand: String): DirPathAbs = new DirPathAbs(arrayUnsafe ++ DirPath.strToStrs(operand))

  override def asStr: String = ife(arrayUnsafe.length == 0, "/", arrayUnsafe.foldLeft("")(_ + "/" + _))
  override def toString: String = "DirPathAbs" + asStr.enParenth
  override def /> (appendStr: String): String = (this / appendStr).asStr
}

object DirPathAbs
{
  def apply(str1: String): DirPathAbs = new DirPathAbs(DirPath.strToStrs(str1))

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
class DirPathRel(val arrayUnsafe: Array[String]) extends DirPath {
  /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(extraPath: DirPathRel): DirPathRel = new DirPathRel(arrayUnsafe ++ extraPath.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirPathRel]] */
  @targetName("append") def /(operand: String): DirPathRel = new DirPathRel(arrayUnsafe ++ DirPath.strToStrs(operand))

  /** Not fully implemented. */
  def </(operand: DirPathRel): DirPathRel = arrayUnsafe.length match {
    case 0 => operand
    case _ => {
      val array = new Array[String](operand.arrayUnsafe.length + 1)
      array(0) = ".."
      operand.arrayUnsafe.copyToArray(array, 1)
      new DirPathRel(array)
    }
  }

  override def asStr: String = arrayUnsafe.length match {
    case 0 => ""
    case 1 => arrayUnsafe(0)
    case _ => arrayUnsafe.mkString("/")
  }

  override def />(appendStr: String): String = ife(arrayUnsafe.length == 0, asStr, asStr / appendStr)
  override def toString: String = "DirPathRel" + asStr.enParenth
}

object DirPathRel
{
  def apply(inp: String*): DirPathRel = new DirPathRel(inp.toArray)
}