/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Directory now exists. It may have already existed or have just been created. */
trait DirExists extends DoneIO, DirsAbs
{ override def reportStr: String = detailStr -- reportTypeStr
  override def detailStr: String = asStr
}

/** Confirmation that a directory existed. */
class DirExisted(val arrayUnsafe: Array[String]) extends DirExists
{ override def reportTypeStr: String = "Directory existed"
}

object DirExisted
{
  def strs(strs: String*): DirExisted = new DirExisted(strs.toArray)

  def str(inp: String): DirExisted = new DirExisted(DirsPath.strToStrs(inp))
}

/** Confirmation that a directory was created. */
case class DirCreated(val arrayUnsafe: Array[String]) extends DirExists
{ override def reportTypeStr: String = "Directory created"
}

object DirCreated
{ def strs(strs: String*): DirCreated = new DirCreated(strs.toArray)

  def str(inp: String): DirCreated = new DirCreated(DirsPath.strToStrs(inp))
}

/** Report of successful JavaScript file write. */
class JsFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript File written"
  override def toString: String = "JsFileWritten" + detailStr.enParenth

  /** Converts this for a succesful js.map file write. */
  def withMap: JsWithMapFilesWritten = JsWithMapFilesWritten(detailStr)
}

object JsFileWritten
{ /** Factory apply method to construct [[JsFileWritten]] report. */
  def apply(detailStr: String): JsFileWritten = new JsFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[JsFileWritten]] */
  given namedTypeEv: ShowType[JsFileWritten] = new ShowFileWritten[JsFileWritten]
  { override val filePrefix: String = "JavaScript"
    override def typeStr: String = "JsFileWritten"
  }
}

/** Report of successful JavaScript file write and its map file write. */
class JsmapFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript map File written"
  override def toString: String = "JsmapFileWritten" + detailStr.enParenth
}

/** Report of successful JavaScript file write. */
class JsWithMapFilesWritten(detailStr: String) extends JsFileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript with map Files written"
  override def toString: String = "JsmapFileWritten" + detailStr.enParenth
}