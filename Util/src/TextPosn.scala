/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse.*

/** A location with text, src code or data. */
case class TextPosn(fileName: String, lineNum :Int, linePosn: Int)
{ /** moves the value from this [[TextPosn]] to the right. */
  def right(num: Int): TextPosn = TextPosn(fileName, lineNum, linePosn + num)

  def right1: TextPosn = TextPosn(fileName, lineNum, linePosn + 1)
  def right2: TextPosn = TextPosn(fileName, lineNum, linePosn + 2)
  def right3: TextPosn = TextPosn(fileName, lineNum, linePosn + 3)
  def addChars(chars: Seq[Char]): TextPosn = TextPosn(fileName, lineNum, linePosn + chars.length)
  def addStr(str: String): TextPosn = TextPosn(fileName, lineNum, linePosn + str.length)
  def newLine: TextPosn = TextPosn(fileName, lineNum + 1, 1)
  def shortStr: String =  lineNum.toString.appendCommas(linePosn.toString)
}

object TextVoid extends TextPosn("Void", 0 , 0)

object StrPosn
{ def apply(lineNum: Int = 1, linePosn: Int = 1): TextPosn = TextPosn("String", lineNum, linePosn)
  def unapply(inp: TextPosn): Option[(Int, Int)] = inp match
  { case TextPosn("String", ln, lp) => Some((ln, lp))
    case _ => None
  }
}

object TextPosn
{ def apply(fileName: String, lineNum: Int, linePosn: Int): TextPosn = new TextPosn(fileName, lineNum, linePosn)
  def fromServer(linePosn: Int = 1, lineNum: Int = 1): TextPosn = TextPosn("Server error", lineNum, linePosn)
  def empty: TextPosn = TextPosn("Empty object", 0, 0)
  def excEmpty: ExcParse = ExcParse("Empty object")
  def failEmpty: Fail[ExcParse] = Fail(excEmpty)

  implicit class TextPosnImplicit(thisTextPosn: TextPosn)
  {
    def parseErr(detail: String): String = thisTextPosn.fileName -- thisTextPosn.lineNum.toString + ", " + thisTextPosn.linePosn.toString + ": " + detail

    /** Produce a failure with an [[ExcLexar]] type. */
    def failParse(detail: String): Fail[ExcParse] = new Fail[ExcParse](ExcParse(thisTextPosn, detail))
    
    /** Produce a failure with a plain [[Exception]] type. */
    def fail(message: String): Fail[Exception] = Fail[Exception](new Exception(message))
    
    /** Produce a failure with an [[ExcLexar]] type. */
    def failLexar(detail: String): Fail[ExcLexar] = new Fail[ExcLexar](ExcLexar(thisTextPosn, detail))

    /** Produce a failure with an [[ExcLexar]] type. */
    def failAst(detail: String): Fail[ExcAst] = new Fail[ExcAst](ExcAst(thisTextPosn, detail))
  }
  
  given persistEV: Persist3Both[String, Int, Int, TextPosn] =
    Persist3Both[String, Int, Int, TextPosn]("TextPosn", "fileName", _.fileName, "lineNum", _.lineNum, "linePosn", _.linePosn, apply)
}

trait TextSpan
{ def startPosn: TextPosn
  def endPosn: TextPosn
  def failExc(detail: String): FailExc = FailExc(startPosn.shortStr -- detail)
}

object TextSpan
{ def empty = new TextSpan{def startPosn = TextPosn.empty; def endPosn = TextPosn.empty }

  /** needs adjusting for empty Seq */
  implicit class FilePosnSeqImplicit(thisSeq: Seq[TextSpan]) extends TextSpan
  { override def startPosn: TextPosn = thisSeq.headOption.fold(TextVoid)(_.startPosn)
    override def endPosn: TextPosn = thisSeq.lastOption.fold(TextVoid)(_.startPosn)
  }
}

/** The purpose of this trait is to give the position span of the syntax from its opening and closing components. */
trait TextSpanMems extends TextSpan
{ def startMem: TextSpan
  def endMem: TextSpan
  override def startPosn: TextPosn = startMem.startPosn
  override def endPosn: TextPosn = endMem.endPosn
}