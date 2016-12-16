
function printCode() {
	//打印标本条码号


    startPrint();
}

var LODOP; //声明为全局变量

function Preview() {//打印预览
	LODOP = getLodop();
	CreateDataBill();
	LODOP.PREVIEW();
}
function Setup() {//打印维护
	LODOP = getLodop();
	LODOP.PRINT_SETUP();
}
function CreateDataBill() {
    var slideid = document.getElementById("slideid").value;
    var slideinfo = document.getElementById("slideinfo").value;
	LODOP = getLodop();
	LODOP.PRINT_INIT("");
	LODOP.SET_PRINT_PAGESIZE(3,"97mm","17mm","A4");

		LODOP.ADD_PRINT_TEXT("8mm","8mm","29mm","5mm","树兰(杭州)医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",10);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("nameText","12mm","8mm","29mm","3mm",slideid);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXTA("nameText","16mm","8mm","29mm","3mm",slideinfo);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);


}
function startPrint() {
	CreateDataBill();
	//开始打印
	// LODOP.PRINT();
	LODOP.PREVIEW();
	// LODOP.PRINT_SETUP();
}