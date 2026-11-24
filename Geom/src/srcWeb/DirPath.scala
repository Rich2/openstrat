/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import pParse.*, annotation.*

/** Common trait for directory paths. */
trait DirPath extends FileSystemPath
{ /** A notification [[String]] to inform that the path doesn't exist. */
  def noExistStr: String = asStr -- "Doesn't exist"

  /** A notification [[String]] to inform that the path is not a directory. */
  def notDirStr: String = asStr -- "is not a directory"

  /** Appends a file name [[FileName]] to produce a file path. */
  @targetName("appendFile") def :/(operand: FileName): FilePathBase

  /** Appends a file name [[String]] to produce a file path. */
  @targetName("appendFile") def :/(operand: String): FilePathBase

  /** Appends a file stem to this directory path. */
  @targetName("appendStem") def :-/(operand: FileNameStem): FileStemPath

  /** Appends a file stem to this directory path. */
  @targetName("appendStem") def :-/(operand: String): FileStemPath
}

/** An absolute Directory path. There are extra methods in the [[webjvm]] that require the JVM, Java Virtual Machine. */
trait DirPathAbs extends DirPath, FileSystemPathAbs
{ /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(newDir: DirPathRel): DirPathAbs = DirPathAbs.fromArray(arrayUnsafe ++ newDir.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirPathRel]] */
  @targetName("append") def /(operand: String): DirPathAbs = DirPathAbs.fromArray(arrayUnsafe :+ operand)

  /** Appends a file name [[FileName]] to produce an absolute file path. */
  @targetName("appendFile") override def :/(operand: FileName): FilePathAbs = new FilePathAbs(arrayUnsafe :+ operand.str)
  
  /** Appends a file name [[String]] to produce an absolute file path. */
  @targetName("appendFile") override def :/(operand: String): FilePathAbs = new FilePathAbs(arrayUnsafe :+ operand)

  /** Appends a file name [[FileName]] to produce an absolute filename stem path. */
  @targetName("appendStem") override def :-/(operand: String): FileStemPathAbs = new FileStemPathAbs(arrayUnsafe :+ operand)

  /** Appends a file name [[FileName]] to produce an absolute filename stem path. */
  @targetName("appendStem") override def :-/(operand: FileNameStem): FileStemPathAbs = new FileStemPathAbs(arrayUnsafe :+ operand.str)

  override def asStr: String = ife(arrayUnsafe.length == 0, "/", arrayUnsafe.foldLeft("")(_ + "/" + _))
  override def toString: String = "DirPathAbs" + asStr.enParenth

  /** Appends a file name to this absolute directory path. */
  @targetName("append") def /+(operand: String): FilePathAbs = new FilePathAbs(arrayUnsafe :+ operand)

  def projPath: ScalaProjPath = new ScalaProjPath(arrayUnsafe)
}

object DirPathAbs
{
  def apply(str1: String): DirPathAbs = new DirPathAbsGen(str1.pathArray)

  def fromArray(arrayUnsafe: Array[String]): DirPathAbs = new DirPathAbsGen(arrayUnsafe)

  given showEv: Show[DirPathAbs] = new Show[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"
    override def strT(obj: DirPathAbs): String = obj.asStr
    override def syntaxDepth(obj: DirPathAbs): Int = 1
    override def show(obj: DirPathAbs, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
      case ShowTyped | ShowStdTypedFields => typeStr + obj.asStr.enParenth
      case _ => obj.asStr
    }
  }

  given unshowEv:Unshow[DirPathAbs] = new Unshow[DirPathAbs]
  { override def typeStr: String = "DirnPathAbs"

    override def fromExpr(expr: Expr): ParseExcEither[DirPathAbs] =  expr match
    { case SlashToken(_) => Right(DirPathAbs.fromArray(Array[String]()))
      case PathToken(_, array) => Right(DirPathAbs.fromArray(array))
      case expr => expr.leftParse("Not an absolute path")
    }
  }

  /** Implementation class for an absolute Directory path. There are extra methods in the [[webjvm]] that require the JVM, Java Virtual Machine. */
  class DirPathAbsGen(val arrayUnsafe: Array[String]) extends DirPathAbs
}

/** An absolute Directory path for a Scala project. There are extra methods in the [[webjvm]] that require the JVM, Java Virtual Machine. */
class ScalaProjPath(val arrayUnsafe: Array[String]) extends DirPathAbs
{ /** The Mill build system out directory, /PathToProjectDir/out. */
  def outDir: DirPathAbs = this / "out"

  /** Module directory in the Mill build system out directory. So the module "Hello" returns /PathToProjectDir/out/Hello. */
  def millModuleDir(moduleStr: String): DirPathAbs = outDir / moduleStr

  /** The FullLinkJS.dest directory. So the module Hello will have the path /PathToProjectDir/out/Hello/fullLinkJS.dest */
  def millFullLinkDir(moduleStr: String): DirPathAbs = millModuleDir(moduleStr) / "fullLinkJS.dest"

  /** The FullLinkJS.dest directory and the file name stem. So the module Hello will have the path /PathToProjectDir/out/Hello/fullLinkJS.dest/main */
  def millFullLinkStem(moduleStr: String): FileStemPathAbs = new FileStemPathAbs(millModuleDir(moduleStr).arrayUnsafe ++ Array("fullLinkJS.dest", "main"))
}

/** Directory path relative. */
class DirPathRel(val arrayUnsafe: Array[String]) extends DirPath
{ /** Move back up the directory hierarchy, dropping the last directory if there is one. */
  def up: DirPathRel = new DirPathRel(arrayUnsafe.dropRight(1))

  /** Appends a relative directory path. There is a name overload that appends a [[String]]. */
  @targetName("append") def /(extraPath: DirPathRel): DirPathRel = new DirPathRel(arrayUnsafe ++ extraPath.arrayUnsafe)

  /** Appends a relative directory path. There is a name overload that appends a [[DirPathRel]] */
  @targetName("append") def /(operand: String): DirPathRel = new DirPathRel(arrayUnsafe ++ operand.pathArray)

  /** From the root of this relative directory path, append the operand directories path. */
  @targetName("fromRootAppendDirs") def </(operand: DirPathRel): DirPathRel = new DirPathRel(topFileAppendArray(operand.arrayUnsafe))

  /** From the root of this relative directory path append the operand directories and filename path. */
  @targetName("fromRootAppendDirsFile") def </>(operand: FilePathRel): FilePathRel = new FilePathRel(topFileAppendArray(operand.arrayUnsafe))

  /** Appends a file name [[FileName]] to produce a relative file path. */
  @targetName("appendFile") override def :/(operand: FileName): FilePathRel = new FilePathRel(arrayUnsafe :+ operand.str)

  /** Appends a file name [[String]] to produce a relative file path. */
  @targetName("appendFile") override def :/(operand: String): FilePathRel = new FilePathRel(arrayUnsafe :+ operand)

  /** Appends a filename stem [[FileNameStem]] to produce a relative file stem path. */
  @targetName("appendStem") override def :-/(operand: FileNameStem): FileStemPathRel = new FileStemPathRel(arrayUnsafe :+ operand.str)

  /** Appends a file name [[String]] to produce a relative file stem path. */
  @targetName("appendStem") override def :-/(operand: String): FileStemPathRel = new FileStemPathRel(arrayUnsafe :+ operand)

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
  @targetName("appendDirsFile") def /> (operand: FilePathRel): FilePathRel = new FilePathRel(arrayUnsafe ++ operand.arrayUnsafe)

  /** Append a directories and file name path. There is a name overload that takes a [[FilePathRel]] as the operand. */
  @targetName("appendDirsFile") def /> (operand: String): FilePathRel = this /> FilePathRel(operand)

  override def asStr: String = arrayUnsafe.length match { case 0 => ""; case _ => arrayUnsafe.mkString("/") }
  override def toString: String = "DirPathRel" + asStr.enParenth
}

object DirPathRel
{ /** Factory apply method for [[DirPathRel]]. */
  def apply(inp: String*): DirPathRel =
  { val newArray = inp.foldLeft(Array[String]())((acc, st) => acc ++ st.pathArray)
    new DirPathRel(newArray)
  }
}