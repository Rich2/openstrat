/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A completed effect. */
trait DoneEff
{ /** The type off effect completed. */
  def effStr: String
  
  /** The specific detail of the effect completed. */
  def detailStr: String
}

trait ShowDoneEff[A <: DoneEff] extends ShowSimple[A]
{
  def actionStr(numSuccesses: Int): String
}

/** Report of successful side effect. */
trait DoneIO extends DoneEff

trait ShowFileWritten[A <: FileWritten] extends ShowDoneEff[A]
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
{ override def effStr: String = "File written"
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
{ override def effStr: String = "POM File written"
}

object PomFileWritten
{ /** Factory apply method to construct [[PomFileWritten]] report. */
  def apply(detailStr: String): PomFileWritten = new PomFileWritten(detailStr)

  implicit val namedTypeEv: ShowType[PomFileWritten] = new ShowFileWritten[PomFileWritten]
  { override val filePrefix: String = "Pom"
  }
}

/** Report of successful POM file write. */
class JsFileWritten(detailStr: String) extends FileWritten(detailStr) {
  override def effStr: String = "JavaScript File written"
}

object JsPomFileWritten
{ /** Factory apply method to construct [[JsFileWritten]] report. */
  def apply(detailStr: String): JsFileWritten = new JsFileWritten(detailStr)

  implicit val namedTypeEv: ShowType[JsFileWritten] = new ShowFileWritten[JsFileWritten] {
    override val filePrefix: String = "JavaScript"
  }
}

/** Directory now exists. It may have already existed or have just been created. */
trait DirExists extends DoneIO

/** Confirmation that a directory existed. */
case class DirExisted(detailStr: String) extends DirExists
{ override def effStr: String = "Directory existed"
}

/** Confirmation that a directory was created. */
case class DirCreated(detailStr: String) extends DirExists
{ override def effStr: String = "Directory created"
}