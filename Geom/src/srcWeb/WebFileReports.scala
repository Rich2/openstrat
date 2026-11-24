/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import java.io.IOException, annotation.unchecked.uncheckedVariance

/** Report of successful HTML file write. */
class HtmlWritten(val arrayUnsafe: Array[String]) extends FileWritten
{ override def fileTypeStr: String = "HTML"
  override def classStr: String = "HtmlWritten"
}

object HtmlWritten
{ /** Factory apply method to construct [[HtmlWritten]] report. */
  def apply(filePath: FilePathAbs): HtmlWritten = new HtmlWritten(filePath.arrayUnsafe)
  
  /** Implicit evidence / instance of [[ShowType]] for [[HtmlWritten]] */
  given namedTypeEv: ShowType[HtmlWritten] = new ShowFileWritten[HtmlWritten]
  { override val filePrefix: String = "HTML"
    override def typeStr: String = "HtmlFileWritten"
  }  
}

/** Report of successful CSS file write. */
class CssWritten(val arrayUnsafe: Array[String]) extends FileWritten
{ override def fileTypeStr: String = "CSS"
  override def classStr: String = "CssWritten"
}

object CssWritten
{ /** Factory apply method to construct [[CssWritten]] report. */
  def apply(filePath: FilePathAbs): CssWritten = new CssWritten(filePath.arrayUnsafe)

  /** Implicit evidence / instance of [[ShowType]] for [[CssWritten]] */
  given showTypeEv: ShowType[CssWritten] = new ShowFileWritten[CssWritten]
  { override val filePrefix: String = "CSS"
    override def typeStr: String = "CssFileWritten"
  }
}

/** Report of successful Jar file write. */
class JarWritten(val arrayUnsafe: Array[String]) extends FileWritten
{ override def fileTypeStr: String = "JAR"
  override def classStr: String = "JarWritten"
}

object JarWritten
{ /** Factory apply method to construct [[JarWritten]] report. */
  def apply(filePath: FilePathAbs): JarWritten = new JarWritten(filePath.arrayUnsafe)

  /** Implicit evidence / instance of [[ShowType]] for [[JarWritten]] */
  given showTypeEv: ShowType[JarWritten] = new ShowFileWritten[JarWritten]
  { override val filePrefix: String = "Jar"
    override def typeStr: String = "JarFileWritten"
  }
}

/** Report of successful POM file write. */
class PomFileWritten(val arrayUnsafe: Array[String]) extends FileWritten
{ override def fileTypeStr: String = "POM"
  override def classStr: String = "PomFileWritten"
}

object PomFileWritten
{ /** Factory apply method to construct [[PomFileWritten]] report. */
  def apply(filePath: FilePathAbs): PomFileWritten = new PomFileWritten(filePath.arrayUnsafe)

  given namedTypeEv: ShowType[PomFileWritten] = new ShowFileWritten[PomFileWritten]
  { override val filePrefix: String = "POM"
    override def typeStr: String = "PomFileWritten"
  }
}