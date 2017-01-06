
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
    var slideid1 = document.getElementById("slideid1").value;
    var slideinfo1 = document.getElementById("slideinfo1").value;
    var slideid2 = document.getElementById("slideid2").value;
    var slideinfo2 = document.getElementById("slideinfo2").value;
    var slideid3 = document.getElementById("slideid3").value;
    var slideinfo3 = document.getElementById("slideinfo3").value;
	LODOP = getLodop();
	LODOP.PRINT_INIT("");
	LODOP.SET_PRINT_PAGESIZE(1,"80mm","24mm","A4");
		LODOP.ADD_PRINT_TEXT("3mm","3mm","27mm","5mm","浙大国际医院");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.SET_PRINT_STYLEA(0,"Bold",1);
		LODOP.ADD_PRINT_TEXTA("nameText","8mm","3mm","29mm","3mm",slideid1);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
		LODOP.ADD_PRINT_TEXTA("nameText","12mm","3mm","29mm","3mm",slideinfo1);
		LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("3mm","30mm","27mm","5mm","浙大国际医院");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText","8mm","30mm","29mm","3mm",slideid2);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXTA("nameText","12mm","30mm","29mm","3mm",slideinfo2);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXT("3mm","57mm","27mm","5mm","浙大国际医院");
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.SET_PRINT_STYLEA(0,"Bold",1);
        LODOP.ADD_PRINT_TEXTA("nameText","8mm","57mm","29mm","3mm",slideid3);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);
        LODOP.ADD_PRINT_TEXTA("nameText","12mm","57mm","29mm","3mm",slideinfo3);
        LODOP.SET_PRINT_STYLEA(0,"FontSize",9);

}
function startPrint() {
	CreateDataBill();
	//开始打印
	// LODOP.PRINT();
	LODOP.PREVIEW();
	// LODOP.PRINT_SETUP();
}