/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Citation attribute for XML / HTML. */
case class CiteAtt(valueStr: String) extends XAttSimple
{ override def name: String = "cite"
}

/** HTML blockquote element. */
trait HtmlBlockQuote extends HtmlTagLines
{ def citeStr: String
  override def tagName: String = "blockquote"
  override def attribs: RArr[XAtt] = RArr(CiteAtt(citeStr))

}

object HtmlBlockQuote
{ /** Factory apply method for HTML blockquote element. */
  def apply(citeStr: String, contents: RArr[XCon], otherAttribs: RArr[XAtt] = RArr()): HtmlBlockQuote = HtmlBlockQuoteGen(citeStr, contents, otherAttribs)

  /** Factory apply method for HTML blockquote element. */
  def apply(citeStr: String, contents: XCon*): HtmlBlockQuote = HtmlBlockQuoteGen(citeStr, contents.toRArr, RArr())

  /** Implementation class for the general case of [[HtmlBlockQuote]] element. */
  class HtmlBlockQuoteGen(val citeStr: String, val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends HtmlBlockQuote
  { override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  }
}

class BlockQuoteAnchored(val quoteBody: String, val citeStr: String) extends HtmlBlockQuote
{
  override def contents: RArr[XCon] =
  { //val sup =  HtmlSup.atts(HtmlA(s"#note$noteNum", s"fn$noteNum"))(IdAtt(s"#src$noteNum"))
    RArr(quoteBody)//, sup)
  }
}

/** HTML short quote element. */
case class HtmlQ(valueStr: String, citeStr: String = "") extends HtmlInline
{ override def tagName: String = "q"
  override def attribs: RArr[XAtt] = ife(citeStr == "", RArr(), RArr(CiteAtt(citeStr)))
  override def contents: RArr[XCon] = RArr(valueStr)
}

case class HtmlNote(num: Int, contextStr: String) extends HtmlP
{ override def attribs: RArr[XAtt] = RArr(IdAtt(s"note$num"))
  override def contents: RArr[XCon] = RArr(contextStr)
}

class NoteTaker
{
  case class Note(num: Int, citeContent: RArr[XCon])
  private val acc: RBuff[Note] = RBuff()
  def len: Int = acc.length

  /** Registers a new footnote. Returns an HTML superscript element with a link to the footnote. */
  def newNote(contextContent: XCon*): HtmlSup = newNote(contextContent.toRArr)

  /** Registers a new footnote. Returns an HTML superscript element with a link to the footnote. */
  def newNote(contextContent: RArr[XCon]): HtmlSup =
  { val num = len + 1
    acc.grow(Note(num, contextContent))
    HtmlSup(HtmlA(s"#note$num", s"fn$num"))
  }

  def blockQuote(quotecontents: XCon*)(citeStr: String, linkLabel: String, noteContents: XCon*): HtmlBlockQuote =
    blockQuote(quotecontents.toRArr, citeStr, linkLabel, noteContents.toRArr)

  def blockQuote(quotecontents: RArr[XCon], citeStr: String, linkLabel: String, noteContents: RArr[XCon]): HtmlBlockQuote =
  { val sup = newNote(HtmlA(citeStr, linkLabel) %: noteContents)
    HtmlBlockQuote(citeStr, quotecontents +% sup)
  }

  /** Produces an HTML section element with the accumulated notes as HTML paragraph elements. */
  def noteSect: HtmlSection =
  { val notes: RArr[HtmlP] = acc.map{nt =>
      val content: RArr[XCon] = HtmlB(s"${nt.num.str}.") %: nt.citeContent
      HtmlP.id(s"note${nt.num.str}", content)
    }

    HtmlSection(HtmlH2("Notes") %: notes)
  }
}

object NoteTaker
{ /** Factory apply method to construct a new  empty [[NoteTaker]]. */
  def apply(): NoteTaker = new NoteTaker
}