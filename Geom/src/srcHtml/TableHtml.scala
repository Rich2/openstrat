/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML table element. */
case class TableHtml(attribs: RArr[HAtt], contents: RArr[RowHtml]) extends HtmlTagLines
{ override def tagName: String = "table"
}

object TableHtml
{ /** Factory apply method for creating HTML table elements. */
  def apply(contents: RowHtml*):  TableHtml = new TableHtml(RArr(), contents.toArr)
  def width100(contents: RowHtml*):  TableHtml = new TableHtml(RArr(WidthCent(100)), contents.toArr)
}

/** HTML TR table row element class. */
trait RowHtml extends HtmlTagLines
{ override def tagName: String = "tr"
}

object RowHtml
{ /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): RowHtml = RowDataHtml()

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): RowHtml = RowDataHtml()

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): RowHtml = RowDataHtml()
}

/** HTML TR table row element class. */
case class RowHeadHtml(attribs: RArr[HAtt], contents: RArr[ThHtml]) extends RowHtml

object RowHeadHtml
{ /** Factory apply method to construct an HTML Table header row */
  def apply(contents: ThHtml*): RowHeadHtml = new RowHeadHtml(RArr(), contents.toRArr)
  
  /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): RowHeadHtml = RowHeadHtml(ThHtml(str1), ThHtml(str2))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): RowHeadHtml = RowHeadHtml(ThHtml(str1), ThHtml(str2), ThHtml(str3))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): RowHeadHtml = RowHeadHtml(ThHtml(str1), ThHtml(str2), ThHtml(str3), ThHtml(str4))
}
/** HTML TR table row element class. */
case class RowDataHtml(attribs: RArr[HAtt], contents: RArr[TdHtml]) extends RowHtml

object RowDataHtml
{ /** Factory apply method to construct an HTML Table data row */
  def apply(contents: TdHtml*): RowDataHtml = new RowDataHtml(RArr(), contents.toRArr)
  
  /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): RowDataHtml = RowDataHtml(TdHtml(str1), TdHtml(str2))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): RowDataHtml = RowDataHtml(TdHtml(str1), TdHtml(str2), TdHtml(str3))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): RowDataHtml = RowDataHtml(TdHtml(str1), TdHtml(str2), TdHtml(str3), TdHtml(str4))
}

/** HTML Table cell. Can be a header cell or a data cell. */
trait CellHtml extends HtmlOwnLine

/** HTML TH table header cell element. */
case class ThHtml(attribs: RArr[HAtt], contents: RArr[XConInedit]) extends CellHtml
{ override def tagName: String = "th"
}

object ThHtml extends HtmlTag
{ /** Factory apply method to construct HTML TH table header cell element form a simple [[String]]. */
  def apply(contents: XConInedit*): ThHtml = new ThHtml(RArr(), contents.toRArr)

  override def tag: String = "th"
}

/** HTML TD table data cell element. */
case class TdHtml(contents: RArr[XCon], attribs: RArr[HAtt]) extends CellHtml
{ override def tagName: String = "td"
}

object TdHtml extends HtmlTag
{ /** Factory apply method to construct HTML TD table data cell element form a simple [[String]]. */
  def apply(str: String) = new TdHtml(RArr(str), RArr())
  
  override def tag: String = "td"
}