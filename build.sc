// build.sc
import mill._, scalalib._, scalajslib._

trait Common extends ScalaModule
{
  def version = "0.0.2snap"  
  def scalacOptions = Seq("-feature", "-language:implicitConversions", "-deprecation", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xfuture", "-Xlint")
}

trait CommonJvm extends Common
{
	def scalaVersion = "2.12.7"
}

trait CommonJs extends ScalaJSModule with Common
{	
	def scalaVersion = "2.12.7"
	def scalaJSVersion = "0.6.26"
}

trait PlatformsModule extends ScalaModule with CommonJvm
{
  outer =>
  
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'srcJvm)

  trait InnerJs extends ScalaJSModule with CommonJs
  {	  
	  def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcJs)
  }
}

object Macros extends PlatformsModule
{ def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
}


object Graphic extends PlatformsModule
{ 
  def moduleDeps = Seq(Macros)
  
  object test extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.6")
    def testFrameworks = Seq("utest.runner.Framework")    
  }  
}

object Core extends PlatformsModule
{
   def moduleDeps = Seq(Graphic)
  
  object test extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.6")
    def testFrameworks = Seq("utest.runner.Framework")    
  }  
}

object FxStrat extends CommonJvm
{ def moduleDeps = Seq(Core)
  def mainClass = Some("ostrat.pFx.DevApp") 
  import mill.modules.Assembly._
  def scalaLibraryIvyDeps = T { Agg.empty }   
}

object JsStrat extends ScalaJSModule with CommonJs
{ def ivyDeps = Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.6")
  def moduleDeps = Seq(Core)
  def sources = T.sources( millSourcePath / 'src, millSourcePath / 'srcPlay)
  def artifactName = "play"  
}
