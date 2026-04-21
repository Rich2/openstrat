/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import pParse.*, annotation.*

/** An absolute Directory path. There are extra methods in the [[webjvm]] that require the JVM, Java Virtual Machine. */
trait DirsAbs extends DirsPath
{ /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(newDir: DirsRel): DirsAbs = DirsAbs.fromArray(arrayUnsafe ++ newDir.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirsRel]] */
  @targetName("append") def /(operand: String): DirsAbs = DirsAbs.fromArray(arrayUnsafe :+ operand)

  /** Appends a file name [[String]] to produce an absolute file path. */
  @targetName("appendFile") def :/(operand: String): DirsFileAbs = new DirsFileAbs(arrayUnsafe :+ operand)

  @targetName("appendStem") override def :-/(operand: String): DirsAbsStem = new DirsAbsStem(arrayUnsafe :+ operand)

  override def asStr: String = ife(arrayUnsafe.length == 0, "/", arrayUnsafe.foldLeft("")(_ + "/" + _))
  override def toString: String = "DirPathAbs" + asStr.enParenth

  /** Appends a file name to this absolute directory path. */
  @targetName("append") def /+(operand: String): DirsFileAbs = new DirsFileAbs(arrayUnsafe :+ operand)

  def projPath: ScalaProjPath = new ScalaProjPath(arrayUnsafe)
}

object DirsAbs
{
  def apply(str1: String): DirsAbs = new DirsAbsGen(DirsPath.strToStrs(str1))

  def fromArray(arrayUnsafe: Array[String]): DirsAbs = new DirsAbsGen(arrayUnsafe)

  given showEv: Show[DirsAbs] = new Show[DirsAbs]
  { override def typeStr: String = "DirnPathAbs"
    override def strT(obj: DirsAbs): String = obj.asStr
    override def syntaxDepth(obj: DirsAbs): Int = 1
    override def show(obj: DirsAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
      case ShowTyped | ShowStdTypedFields => typeStr + obj.asStr.enParenth
      case _ => obj.asStr
    }
  }

  given unshowEv:Unshow[DirsAbs] = new Unshow[DirsAbs]
  { override def typeStr: String = "DirnPathAbs"

    override def fromExpr(expr: Expr): ExcMon[DirsAbs] =  expr match
    { case SlashToken(_) => Succ(DirsAbs.fromArray(Array[String]()))
      case PathToken(_, array) => Succ(DirsAbs.fromArray(array))
      case expr => expr.failExc("Not an absolute path")
    }
  }

  /** Implementation class for an absolute Directory path. There are extra methods in the [[webjvm]] that require the JVM, Java Virtual Machine. */
  class DirsAbsGen(val arrayUnsafe: Array[String]) extends DirsAbs
}

/** An absolute Directory path for a Scala project. There are extra methods in the [[webjvm]] that require the JVM, Java Virtual Machine. */
class ScalaProjPath(val arrayUnsafe: Array[String]) extends DirsAbs
{ /** The Mill build system out directory. */
  def out: DirsAbs = this / "out"

  /** Module directory in the Mill build system out directory. */
  def outModule(moduleStr: String): DirsAbs = out / moduleStr

  def outFullLink(moduleStr: String): DirsAbsStem = new DirsAbsStem(outModule(moduleStr).arrayUnsafe ++ Array("fullLinkJS.dest", "main"))
}