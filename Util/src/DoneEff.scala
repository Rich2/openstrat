/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*

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

/** Report of successful file write. */
trait FileWritten extends DoneIO
{ override def effStr: String = "File written"
}

object FileWritten
{ /** Factory apply method to construct [[FileWritten]] report. */
  def apply(detailStr: String): FileWritten = FileWrittenJust(detailStr)

  implicit val namedTypeEv: ShowType[FileWritten] = new ShowDoneEff[FileWritten]
  { override def typeStr: String = "FileWritten"
    override def strT(obj: FileWritten): String = "File written"

    override def actionStr(numSuccesses: Int): String =
    { val fw: String = ife(numSuccesses == 1, "file", "files")
      s"$fw written successfully"
    }
  }
}

/** Report of a successful write that is not copied or moved. */
case class FileWrittenJust(detailStr: String) extends FileWritten

/** Report of a successful file copy. */
case class FileCopied(detailStr: String) extends FileWritten
{ override def effStr: String = "File copied"
}

object FileCopied
{
  implicit def errBiSummaryEv: ErrBiSummary[IOExc, FileCopied] = eba => eba.succNum.pluralisation("file") -- "copied." -- eba.errNum.pluralisation("fail") + "."
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