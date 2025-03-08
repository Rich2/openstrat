/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Report of a successfully completed effect. Used for pure side effects that return [[Unit]]. */
trait EffectReport
{ /** The type off effect completed. */
  def reportTypeStr: String
  
  /** The specific detail of the effect completed. */
  def detailStr: String

  def reportStr: String
}

/** [[Show]] type class instances for [[EffectReport]]. Their main purpose is not to [[Perist]] [[EffectReport]]s but to be able to accumulate and summarise
 * them. */
trait ShowEffectReport[A <: EffectReport] extends ShowSimple[A]
{ /** Describes the action of the successful effect that is being reported. */
  def actionStr(numSuccesses: Int): String
}

/** Report of successful side effect. */
trait DoneIO extends EffectReport

trait ShowFileWritten[A <: FileWritten] extends ShowEffectReport[A]
{
  def filePrefix: String

  override def typeStr: String = filePrefix + "FileWritten"
  override def strT(obj: A): String = typeStr

  override def actionStr(numSuccesses: Int): String =
  { val filePrefix2 = ife(filePrefix == "", "", filePrefix + " ")
    val fw: String = ife(numSuccesses == 1, "file", "files")
    s"$filePrefix2$fw written successfully"
  }
}

/** Report of successful file write. */
class FileWritten(val detailStr: String) extends DoneIO
{ override def reportTypeStr: String = "File written"
  override def toString: String = "FileWritten" + detailStr.enParenth
  override def reportStr: String = reportTypeStr -- "to" -- detailStr
}

object FileWritten
{ /** Factory apply method to construct [[FileWritten]] report. */
  def apply(detailStr: String): FileWritten = new FileWritten(detailStr)

  implicit val namedTypeEv: ShowType[FileWritten] = new ShowFileWritten[FileWritten]
  { override def filePrefix: String = ""
  }
}

/** Report of successful POM file write. */
class PomFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "POM File written"
  override def toString: String = "PomFileWritten" + detailStr.enParenth
}

object PomFileWritten
{ /** Factory apply method to construct [[PomFileWritten]] report. */
  def apply(detailStr: String): PomFileWritten = new PomFileWritten(detailStr)

  implicit val namedTypeEv: ShowType[PomFileWritten] = new ShowFileWritten[PomFileWritten]
  { override val filePrefix: String = "Pom"
  }
}

/** Report of successful JavaScript file write. */
class JsFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript File written"
  override def toString: String = "JsFileWritten" + detailStr.enParenth
}

object JsFileWritten
{ /** Factory apply method to construct [[JsFileWritten]] report. */
  def apply(detailStr: String): JsFileWritten = new JsFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[JsFileWritten]] */
  implicit val namedTypeEv: ShowType[JsFileWritten] = new ShowFileWritten[JsFileWritten]
  { override val filePrefix: String = "JavaScript"
  }
}

/** Report of successful HTML file write. */
class HtmlFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "HTML File written"
  override def toString: String = "HtmlFileWritten" + detailStr.enParenth
}

object HtmlFileWritten
{ /** Factory apply method to construct [[HtmlFileWritten]] report. */
  def apply(detailStr: String): HtmlFileWritten = new HtmlFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[HtmlFileWritten]] */
  implicit val namedTypeEv: ShowType[HtmlFileWritten] = new ShowFileWritten[HtmlFileWritten]
  { override val filePrefix: String = "Html"
  }
}

/** Report of successful CSSL file write. */
class CssFileWritten(detailStr: String) extends FileWritten(detailStr) {
  override def reportTypeStr: String = "CSS File written"
  override def toString: String = "CssFileWritten" + detailStr.enParenth
}

object CssFileWritten
{ /** Factory apply method to construct [[CssFileWritten]] report. */
  def apply(detailStr: String): CssFileWritten = new CssFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[CssFileWritten]] */
  implicit val namedTypeEv: ShowType[CssFileWritten] = new ShowFileWritten[CssFileWritten]
  { override val filePrefix: String = "Css"
  }
}

/** Directory now exists. It may have already existed or have just been created. */
trait DirExists extends DoneIO
{ override def reportStr: String = detailStr -- reportTypeStr
}

/** Confirmation that a directory existed. */
case class DirExisted(detailStr: String) extends DirExists
{ override def reportTypeStr: String = "Directory existed"
}

/** Confirmation that a directory was created. */
case class DirCreated(detailStr: String) extends DirExists
{ override def reportTypeStr: String = "Directory created"
}