/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Report of successful HTML file write. */
class HtmlFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "HTML File written"
  override def toString: String = "HtmlFileWritten" + detailStr.enParenth
}

object HtmlFileWritten
{ /** Factory apply method to construct [[HtmlFileWritten]] report. */
  def apply(detailStr: String): HtmlFileWritten = new HtmlFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[HtmlFileWritten]] */
  given namedTypeEv: ShowType[HtmlFileWritten] = new ShowFileWritten[HtmlFileWritten]
  { override val filePrefix: String = "HTML"
    override def typeStr: String = "HtmlFileWritten"
  }
}

/** Report of successful CSS file write. */
class CssFileWritten(detailStr: String) extends FileWritten(detailStr) {
  override def reportTypeStr: String = "CSS File written"
  override def toString: String = "CssFileWritten" + detailStr.enParenth
}

object CssFileWritten
{ /** Factory apply method to construct [[CssFileWritten]] report. */
  def apply(detailStr: String): CssFileWritten = new CssFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[CssFileWritten]] */
  given namedTypeEv: ShowType[CssFileWritten] = new ShowFileWritten[CssFileWritten]
  { override val filePrefix: String = "CSS"
    override def typeStr: String = "CssFileWritten"
  }
}

/** Report of successful Jar file write. */
class JarFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "Jar File written"
  override def toString: String = "JarFileWritten" + detailStr.enParenth
}

object JarFileWritten
{ /** Factory apply method to construct [[JarFileWritten]] report. */
  def apply(detailStr: String): JarFileWritten = new JarFileWritten(detailStr)

  /** Implicit evidence / instance of [[ShowType]] for [[JarFileWritten]] */
  given namedTypeEv: ShowType[JarFileWritten] = new ShowFileWritten[JarFileWritten]
  { override val filePrefix: String = "Jar"
    override def typeStr: String = "JarFileWritten"
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

  given namedTypeEv: ShowType[PomFileWritten] = new ShowFileWritten[PomFileWritten]
  { override val filePrefix: String = "POM"
    override def typeStr: String = "PomFileWritten"
  }
}

/** Report of successful JavaScript file write. */
class JsFileWritten(detailStr: String) extends FileWritten(detailStr)
{ override def reportTypeStr: String = "JavaScript File written"
  override def toString: String = "JsFileWritten" + detailStr.enParenth

  /** Converts this for a successful js.map file write. */
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