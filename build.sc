// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._, publish._

trait Common extends ScalaModule
{ def version = "0.2.1snap"
def scalaVersion = "2.13.3"
  def scalacOptions = Seq("-feature", "-language:higherKinds,implicitConversions", "-deprecation", "-Ywarn-value-discard", "-encoding", "UTF-8", "-unchecked", "-Xlint")
}

trait CommonJvm extends Common
{ 
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm, millSourcePath / 'srcExs, millSourcePath / 'srcExsJvm)

  trait InnerTests extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.7.5")
    def testFrameworks = Seq("utest.runner.Framework") 
    def sources = T.sources(millSourcePath / 'src)
    def resources = T.sources(millSourcePath / 'res)
  }
}

trait CommonJs extends ScalaJSModule with Common
{ def scalaJSVersion = "1.1.0"
  //def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcJs)
  def ivyDeps = Agg(ivy"org.scala-js::scalajs-dom_sjs1:1.1.0")
}

object UtilMacros extends CommonJvm// with PublishModule
{ def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
  def sources = T.sources(Util.millSourcePath / 'srcMacros)
}

object UtilMacrosJs extends CommonJs
{ def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
  def sources = T.sources(Util.millSourcePath / 'srcMacros)
}

object Util extends CommonJvm
{ def moduleDeps = Seq(UtilMacros)
  def mainClass = Some("ostrat.WebPage1")
  object test extends InnerTests  
}

object UtilJs extends CommonJs
{ def moduleDeps = Seq(UtilMacrosJs)
  def sources = T.sources(Util.millSourcePath / 'src)
}

object Graphics extends CommonJvm
{ def moduleDeps = Seq(Util)
  //def ivyDeps = Agg(ivy"org.openjfx:javafx:14")
  object test extends InnerTests  
}

object GraphicsJs extends CommonJs
{ def moduleDeps = Seq(UtilJs)
def sources = T.sources(Graphics.millSourcePath / 'src, Graphics.millSourcePath / 'srcJs)
}

object Tiling extends CommonJvm
{ def moduleDeps = Seq(Graphics)  
  object test extends InnerTests
  def sources = T.sources(Tiling.millSourcePath / 'src)
}

object TilingJs extends CommonJs
 {  def moduleDeps = Seq(GraphicsJs)

 }

object World extends CommonJvm
{ def moduleDeps = Seq(Tiling)  

  object test extends InnerTests
}

object WorldJs extends CommonJs
{ def moduleDeps = Seq(TilingJs)
}

object Dev extends CommonJvm
{ def moduleDeps = Seq(World)
  def mainClass = Some("ostrat.pFx.DevApp")
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm)
  def resources = T.sources(millSourcePath / 'User)  
}

object DevJs extends CommonJs
{ def moduleDeps = Seq(WorldJs)
  def sources = T.sources(millSourcePath / 'src, Dev.millSourcePath / 'srcLearn)
} 
/*def run() = Dev.runBackground()
def test = Util.test
def jsfast = Dev.js.fastOpt
def jsfull = Dev.js.fullOpt*/