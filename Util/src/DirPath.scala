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
  @targetName("appendToStr")def /%(operand: DirsRel): String

  /** Appends a [[String]] and converts the path to a [[String]] */
  @targetName("appendToStr") def /%(appendStr: String): String
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

/** An absolute Directory path. */
class DirsAbs(val arrayUnsafe: Array[String]) extends DirPath
{ /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(newDir: DirsRel): DirsAbs = new DirsAbs(arrayUnsafe ++ newDir.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirsRel]] */
  @targetName("append") def /(operand: String): DirsAbs = new DirsAbs(arrayUnsafe ++ DirPath.strToStrs(operand))

  override def asStr: String = ife(arrayUnsafe.length == 0, "/", arrayUnsafe.foldLeft("")(_ + "/" + _))
  override def toString: String = "DirPathAbs" + asStr.enParenth
  @targetName("appendToStr") override def /%(operand: DirsRel): String =  (this / operand).asStr
  @targetName("appendToStr") override def /%(appendStr: String): String = (this / appendStr).asStr
}

object DirsAbs
{
  def apply(str1: String): DirsAbs = new DirsAbs(DirPath.strToStrs(str1))

  implicit val showEv: Show[DirsAbs] = new Show[DirsAbs]
  { override def typeStr: String = "DirnPathAbs"
    override def strT(obj: DirsAbs): String = obj.asStr
    override def syntaxDepth(obj: DirsAbs): Int = 1
    override def show(obj: DirsAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
      case ShowTyped | ShowStdTypedFields => typeStr + obj.asStr.enParenth
      case _ => obj.asStr
    }
  }

  implicit val unshowEv:Unshow[DirsAbs] = new Unshow[DirsAbs]
  { override def typeStr: String = "DirnPathAbs"

    override def fromExpr(expr: Expr): ExcMon[DirsAbs] =  expr match
    { case SlashToken(_) => Succ(new DirsAbs(Array[String]()))
      case PathToken(_, array) => Succ(new DirsAbs(array))
      case expr => expr.failExc("Not an absolute path")
    }
  }
}

/** Directory path relative. */
class DirsRel(val arrayUnsafe: Array[String]) extends DirPath
{ /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(extraPath: DirsRel): DirsRel = new DirsRel(arrayUnsafe ++ extraPath.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirsRel]] */
  @targetName("append") def /(operand: String): DirsRel = new DirsRel(arrayUnsafe ++ DirPath.strToStrs(operand))

  /** From the root of this relative directory path, append the operand directories path. */
  @targetName("fromRootAppendDirs") def </(operand: DirsRel): DirsRel = new DirsRel(topFileAppendArray(operand.arrayUnsafe))

  /** From the root of this relative directory path append the operand directories and filename path. */
  @targetName("fromRootAppendDirsFile") def </>(operand: DirsFileRel): DirsFileRel = new DirsFileRel(topFileAppendArray(operand.arrayUnsafe))

  /** From the root of this relative directory path append the operand directories and then convert to a [[String]]. */
  @targetName("fromRootAppendDirsStr") def </%(operand: DirsRel): String = (this </ operand).asStr

  /** Utility method for fromRootAppendDirs and fromRootAppendDirsFile methods. Creates the backing [[Array]] for the returned classes. */
  def topFileAppendArray(operand: Array[String]): Array[String] =
  { val thisLen: Int = arrayUnsafe.length
    val opLen: Int = operand.length
    def loop(i: Int): Int = if (i < thisLen && i < opLen && arrayUnsafe(i) == operand(i)) loop(i + 1) else i
    val co = loop(0)
    val newArray: Array[String] = new Array[String](thisLen + opLen - 2 * co)
    iUntilForeach(thisLen - co) { i => newArray(i) = ".." }
    Array.copy(operand, co, newArray, thisLen - co, opLen - co)
    newArray
  }

  /** Append a directories and file name path. There is a name overload that takes the [[String]] representation as the operand. */
  @targetName("appendDirsFile") def /> (operand: DirsFileRel): DirsFileRel = new DirsFileRel(arrayUnsafe ++ operand.arrayUnsafe)

  /** Append a directories and file name path. There is a name overload that takes a [[DirsFileRel]] as the operand. */
  @targetName("appendDirsFile") def /> (operand: String): DirsFileRel = this /> DirsFileRel(operand)

  /** Append a directories and file name path, then convert to [[String]]. There is a name overload that takes the [[String]] representation as the operand. */
  @targetName("appendDirsFileStr") def </>%(operand: DirsFileRel): String = (this </> operand).asStr

  /** Append a directories and file name path, then convert to [[String]]. There is a name overload that takes a [[DirsFileRel]] as the operand. */
  @targetName("appendDirsFileStr") def </>%(operand: String): String = (this </> DirsFileRel(operand)).asStr

  override def asStr: String = arrayUnsafe.length match { case 0 => ""; case _ => arrayUnsafe.mkString("/") }
  @targetName("appendToStr") override def /%(operand: DirsRel): String = (this / operand).asStr
  @targetName("appendToStr") override def /%(appendStr: String): String = ife(arrayUnsafe.length == 0, asStr, asStr / appendStr)
  override def toString: String = "DirPathRel" + asStr.enParenth
}

object DirsRel
{ /** Factory apply method for [[DirsRel]]. */
  def apply(inp: String*): DirsRel =
  { val newArray = inp.foldLeft(Array[String]())((acc, st) => acc ++ DirPath.strToStrs(st))
    new DirsRel(newArray)
  }
}

/** Relative directory (or folder) path and file name. */
class DirsFileRel(val arrayUnsafe: Array[String])
{
  def asStr: String = arrayUnsafe.length match {
    case 0 => excep("File name backing array must have at least 1 [[String]] element.")
    case _ => arrayUnsafe.mkString("/")
  }
}

object DirsFileRel
{ /** Factory apply method for [[DirsRel]]. */
  def apply(str0: String, inp: String*): DirsFileRel =
  { val newArray = (str0 +: inp).foldLeft(Array[String]())((acc, st) => acc ++ DirPath.strToStrs(st))
    new DirsFileRel(newArray)
  }
}