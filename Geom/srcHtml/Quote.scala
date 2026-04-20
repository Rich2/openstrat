/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Citation attribute for XML / HTML. */
case class CiteAtt(valueStr: String) extends XAttShort
{ override def name: String = "cite"
}

/** HTML blockquote element. */
trait BlockQuoteHtml extends HtmlTagLines
{ def citeStr: String
  override def tagName: String = "blockquote"
  override def attribs: RArr[XAtt] = RArr(CiteAtt(citeStr))

}

object BlockQuoteHtml
{ /** Factory apply method for HTML blockquote element. */
  def apply(citeStr: String, contents: RArr[XCon], otherAttribs: RArr[XAtt] = RArr()): BlockQuoteHtml = BlockQuoteHtmlGen(citeStr, contents, otherAttribs)

  /** Factory apply method for HTML blockquote element. */
  def apply(citeStr: String, contents: XCon*): BlockQuoteHtml = BlockQuoteHtmlGen(citeStr, contents.toRArr, RArr())

  /** Implementation class for the general case of [[BlockQuoteHtml]] element. */
  class BlockQuoteHtmlGen(val citeStr: String, val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends BlockQuoteHtml
  { override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  }
}

/** HTML short quote element. */
case class QHtml(valueStr: String, citeStr: String = "") extends HtmlInedit
{ override def tagName: String = "q"
  override def attribs: RArr[XAtt] = ife(citeStr == "", RArr(), RArr(CiteAtt(citeStr)))
  override def contents: RArr[XCon] = RArr(valueStr)
}

/** HTML paragraph with a note ID attribute. */
case class NoteHtml(num: Int, contextStr: String) extends PHtml
{ override def attribs: RArr[XAtt] = RArr(IdAtt(s"note$num"))
  override def contents: RArr[XCon] = RArr(contextStr)
}

class NoteTaker
{
  case class Note(num: Int, citeContent: RArr[XCon])
  private val acc: RBuff[Note] = RBuff()
  def len: Int = acc.length

  /** Registers a new footnote. Returns an HTML superscript element with a link to the footnote. */
  def newNote(contextContent: XCon*): SupHtml = newNote(contextContent.toRArr)

  /** Registers a new footnote. Returns an HTML superscript element with a link to the footnote. */
  def newNote(contextContent: RArr[XCon]): SupHtml =
  { val num = len + 1
    acc.grow(Note(num, contextContent))
    SupHtml(AHtml(s"#note$num", s"fn$num"))
  }

  /** Creates an HTML block quote with a footnote. */
  def blockQuote(quotecontents: XCon*)(citeStr: String, linkLabel: String, noteContents: XCon*): BlockQuoteHtml =
    blockQuote(quotecontents.toRArr, citeStr, linkLabel, noteContents.toRArr)

  /** Creates an HTML block quote with a footnote. */
  def blockQuote(quotecontents: RArr[XCon], citeStr: String, linkLabel: String, noteContents: RArr[XCon]): BlockQuoteHtml =
  { val sup = newNote(AHtml(citeStr, linkLabel) %: noteContents)
    BlockQuoteHtml(citeStr, quotecontents +% sup)
  }

  /** Produces an HTML section element with the accumulated notes as HTML paragraph elements. */
  def noteSect: SectionHtml =
  { val notes: RArr[PHtml] = acc.map{nt =>
      val content: RArr[XCon] = BHtml(s"${nt.num.str}.") %: nt.citeContent
      PHtml.id(s"note${nt.num.str}", content)
    }

    SectionHtml(H2Html("Notes") %: notes)
  }
}

object NoteTaker
{ /** Factory apply method to construct a new  empty [[NoteTaker]]. */
  def apply(): NoteTaker = new NoteTaker
}