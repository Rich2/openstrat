/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait HtmlEl extends XmlEl with XCon
trait HNotVoid extends HtmlEl with XNotVoid

trait HEmpty extends HtmlEl with XEmpty

case class HMetaNC(name: String, content: String) extends HMeta { override def atts = Seq(XAtt("name", name), XAtt("content", content)) }

object HHead
{ def apply(memsIn: XCon *): HHead = new HHead { override def mems = memsIn }
  def get(memsIn: Seq[XCon]): HHead = new HHead { override def mems = memsIn }
}

trait HHead extends HNotVoid
{ override def tag: String = "head"
  override def atts: Seq[XAtt] = Seq[XAtt]()
}

object HBody
{ def apply(memsIn: XCon *): HBody = new HBody { override def mems: Seq[XCon] = memsIn }
  def get(memsIn: Seq[XCon]): HBody = new HBody { override def mems: Seq[XCon] = memsIn }
}

trait HBody extends HNotVoid
{ override def tag: String = "body"
  override def atts: Seq[XAtt] = Seq[XAtt]()
}

object HStyle
{ def r(rules: CssRule*): HStyle = HStyle(rules)
}

case class HStyle (rules: Seq[CssRule]) extends HNotVoid 
{ def tag = "style"
  def mems: Seq[IndentCon] = rules
}

object HSpan
{
  def r(mems: XCon*)(attsIn: XAtt*): HSpan = new HSpan(mems)
  { override def atts: Seq[XAtt] = attsIn
  }
}

case class HSpan(mems: Seq[XCon]) extends HNotVoid { def tag = "span" }

case class HCanvas(id: String) extends HEmpty
{ def tag = "canvas"
  override def atts: Seq[XAtt] = Seq(IdAtt(id))
}

object HSvg
{ def r(widthIn: Int, heightIn: Int, svgMemsIn: XCon*): HSvg = new HSvg(widthIn, heightIn, svgMemsIn)
}
case class HSvg(width: Int, height: Int, svgMems: Seq[XCon]) extends HNotVoid
{ def tag = "svg"
  override def atts = Seq(XAtt("width", width.toString), XAtt("height", height.toString))
  override def mems: Seq[XCon] = svgMems
}

trait JCon extends XCon 

object HtmlJs
{
  def apply(jsMemsIn: JCon *)(attsIn: XAtt *): HtmlJs = new HtmlJs
  { override def jsMems = jsMemsIn
    override val atts = attsIn
  }
}

trait HtmlJs extends HNotVoid
{ def tag = "script"
  def jsMems: Seq[JCon]
  override def mems: Seq[XCon] = jsMems
}
object HtmlP
{
  def apply(str: String, attsIn: XAtt*): HtmlP = new HtmlP
  { override val atts: Seq[XAtt] = attsIn
    override def mems: Seq[XCon] = Seq(str)
  }

  def r(memsIn: XCon*)(attsIn: XAtt*): HtmlP = new HtmlP
  { override val atts: Seq[XAtt] = attsIn
    override def mems: Seq[XCon] = memsIn
  }
}

trait HtmlP extends HNotVoid
{ def tag = "p"
}

object HtmlDiv
{
  def apply(hMemsIn: XCon *)(attribsIn: XAtt *): HtmlDiv = new HtmlDiv(hMemsIn)
  { // val hMems: Seq[XCon] = hMemsIn
    //override def mems: Seq[XCon] = hMemsIn
    override val atts = attribsIn
  }
}

abstract class HtmlDiv(hMemsIn: Seq[XCon]) extends HNotVoid
{ def tag = "div"
  //def hMems: Seq[XCon]
  override def mems: Seq[XCon] = hMemsIn
}

object HUList
{
  def apply(memsIn: XCon *)(attsIn: XAtt *): HUList = new HUList
  { val mems = memsIn
    override def atts: Seq[XAtt] = attsIn
  }
}

trait HUList extends HNotVoid { def tag = "ul" }

case class HLinkItem(fileName: String, label: String) extends HNotVoid 
{ def tag = "li"
  override def mems: Seq[XCon] = Seq(HLink(fileName, label))
}

case class HtmlFile(fileName: String) extends XCon
{ val lines = io.Source.fromFile(fileName).getLines().toSeq
  def out(ind: Int): String =  lines.foldLeft("")((acc, el) => acc.nli + ind.spaces + el)
}

//object HFooter
//{
//   def apply(memsIn: XCon *) = new
//}
//
//trait HFooter extends HtmlDiv
//{
//   //def tag = "footer"
//   //def mems: Seq[XCon] = memsIn
//}