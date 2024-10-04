/**
 * 
 */

const clientarea = document.getElementById("clientarea"); //정적변수라서 바ㄱ뀌ㅣ지 않아도 되ㅓㅅ const
const serverarea = document.querySelector("#serverarea");
const controlBtns = document.querySelectorAll(".control"); //모든 것들을 가지고 있음 

const watchJob = async ()=>{ //async 비동기 await를 쓰려면 async를 사용해야함 기다리겠다는 뜻 	
	clientarea.innerHTML = new Date();
	let resp = await fetch("../../../now2.do"); 
	if(resp.ok){
		let jsonObj = await resp.json(); 
		serverarea.innerHTML = jsonObj.now;
		
	}else{
		let errMsg = await resp.text();
		serverarea.innerHTML = errMsg; 
		controlBtns.forEach(b=>{
			if(b.dataset['role'] == "stop" )//연산배열구조 , 연관배열구조
				b.click();
			
			}) 
	}
}

let intervalId = null; 

const watchStart = (btn)=>{ //target은 버튼이라 btn
	//함수의 레퍼런스 
	intervalId = setInterval(watchJob, 1000);
}

const watchStop = (btn)=>{
	if(intervalId)
	clearInterval(intervalId);
}

controlBtns.forEach((btn,index)=>{
	//작업 == 함수 
	//작업이 2개임 그래서 하나씩 가져와야함 
							//이벤트 핸들러 
							//구조분해문법 {} 객체의 형태를 뜯어보는 문법
	btn.addEventListener("click",({target})=>{
		//console.log(event.target === btn)	//버튼 한개마다 이벤트를 하나씩 부여해야함 //눌렀을 떄에  
		let role = target.dataset.role;
		controlBtns.forEach(b=>b.classList.toggle("d-none"));
		if(role == "start"){
			watchStart(target);
			
		}else if(role == "stop"){
			watchStop(target);
			
		}
	}); 
});


