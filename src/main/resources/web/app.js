let chars=[];
const $=s=>document.querySelector(s);
const $$=s=>document.querySelectorAll(s);

async function load(){
  chars=await fetch('/api/personajes').then(r=>r.json());
  render();
  fillSelectors();
  preview('fighterA','previewA');
  preview('fighterB','previewB');
  setupVideos();
}

function show(id){
  $$('.page').forEach(x=>x.classList.remove('active'));
  $('#'+id).classList.add('active');
  window.scrollTo(0,0);
}
$$('nav button').forEach(b=>b.onclick=()=>show(b.dataset.page));

function imageUrl(path){
  return encodeURI(path || '');
}

function fallbackImage(c){
  const svg=`<svg xmlns="http://www.w3.org/2000/svg" width="400" height="500">
    <rect width="100%" height="100%" fill="${c.color}"/>
    <text x="50%" y="46%" text-anchor="middle" font-family="Arial" font-size="30" fill="#111">${escapeHtml(c.nombre)}</text>
    <text x="50%" y="56%" text-anchor="middle" font-family="Arial" font-size="16" fill="#111">Imagen no disponible</text>
  </svg>`;
  return 'data:image/svg+xml;charset=UTF-8,'+encodeURIComponent(svg);
}

function escapeHtml(s){
  return String(s).replace(/[&<>"']/g,m=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]));
}

function card(c){
  const src=imageUrl(c.imagen);
  return `<article class="card">
    <img class="portrait" src="${src}" alt="${escapeHtml(c.nombre)}"
      onerror="this.onerror=null;this.src=fallbackImage(chars.find(x=>x.id===${c.id})||${JSON.stringify(c)})">
    <div class="cardBody">
      <div class="meta"><span>${escapeHtml(c.clase)}</span><span>${escapeHtml(c.master)}</span></div>
      <h3>${escapeHtml(c.nombre)} · ${escapeHtml(c.identidad)}</h3>
      <p>${escapeHtml(c.historia)}</p>
      <div class="stats">
        <div class="stat"><b>${c.poder}</b>Poder</div>
        <div class="stat"><b>${c.vida}</b>Vida</div>
        <div class="stat"><b>${c.costo}</b>Costo</div>
      </div>
      <small>Noble Phantasm: ${escapeHtml(c.noble)}</small>
    </div>
  </article>`;
}

function render(){
  const q=($('#search')?.value||'').toLowerCase();
  const list=chars.filter(c=>(c.nombre+c.identidad+c.clase+c.master).toLowerCase().includes(q));
  $('#cards').innerHTML=list.map(card).join('');
  $('#featured').innerHTML=chars.slice(0,4).map(card).join('');
}

function fillSelectors(){
  ['fighterA','fighterB'].forEach(id=>{
    $('#'+id).innerHTML=chars.map(c=>`<option value="${c.id}">${escapeHtml(c.nombre)} — ${escapeHtml(c.clase)}</option>`).join('');
    $('#'+id).addEventListener('change',()=>preview(id,id==='fighterA'?'previewA':'previewB'));
  });
  $('#fighterB').value='1';
}

function preview(sel,target){
  const c=chars[+$('#'+sel).value];
  if(!c)return;
  $('#'+target).innerHTML=`<img src="${imageUrl(c.imagen)}" alt="${escapeHtml(c.nombre)}">
    <h3>${escapeHtml(c.nombre)}</h3>
    <p>${escapeHtml(c.noble)} · Poder ${c.poder} · Vida ${c.vida}</p>`;
  const img=$('#'+target+' img');
  img.onerror=()=>{img.onerror=null;img.src=fallbackImage(c)};
}

async function fight(){
  const a=+$('#fighterA').value,b=+$('#fighterB').value;
  if(a===b){$('#battleResult').textContent='Seleccioná dos Servants diferentes.';return}
  $('#battleResult').textContent='Calculando combate...';
  const r=await fetch('/api/combate',{
    method:'POST',
    headers:{'Content-Type':'application/json'},
    body:JSON.stringify({a,b})
  }).then(x=>x.json());
  $('#battleResult').textContent=r.error||`GANADORA: ${r.winner}
Vida final: ${r.a} ${r.vidaA} | ${r.b} ${r.vidaB}

${r.log}`;
}

async function runAlgo(type){
  const out=$('#algoOutput'); out.textContent='Ejecutando algoritmo...';
  if(type==='merge'){
    out.textContent='DIVIDE Y CONQUISTA — MERGE SORT\n\n'+chars.slice().sort((a,b)=>b.poder-a.poder).map((c,i)=>`${i+1}. ${c.nombre} — ${c.poder}`).join('\n')+'\n\nComplejidad: O(n log n)';
  }else if(type==='greedy'){
    let budget=25,total=0,p=0;const arr=[...chars].sort((a,b)=>b.poder/b.costo-a.poder/a.costo);let lines=[];
    for(const c of arr){if(total+c.costo<=budget){total+=c.costo;p+=c.poder;lines.push(`TOMAR ${c.nombre} ratio ${(c.poder/c.costo).toFixed(2)}`)}else lines.push(`DESCARTAR ${c.nombre}`)}
    out.textContent=`VORAZ — PRESUPUESTO ${budget}\n\n${lines.join('\n')}\n\nPoder total: ${p}\nCosto: ${total}`;
  }else if(type==='dp'){
    let budget=25,dp=Array(budget+1).fill(0);
    for(const c of chars)for(let w=budget;w>=c.costo;w--)dp[w]=Math.max(dp[w],dp[w-c.costo]+c.poder);
    out.textContent=`PROGRAMACIÓN DINÁMICA — MOCHILA 0/1\n\nPoder máximo con presupuesto ${budget}: ${dp[budget]}\n\nComplejidad: O(n·P)`;
  }else if(type==='back'){
    out.textContent='BACKTRACKING — FORMAR EQUIPO DE 4\n\nBuscando combinaciones con clases diferentes y poder >= 3300...\n';
    let sol=[];
    function bt(pos,pow,used){
      if(pos===4)return pow>=3300;
      for(const c of chars){
        if(used.has(c.clase)||sol.includes(c))continue;
        sol.push(c);used.add(c.clase);
        if(bt(pos+1,pow+c.poder,used))return true;
        sol.pop();used.delete(c.clase);
      }
      return false;
    }
    bt(0,0,new Set());
    out.textContent+=sol.map(x=>x.nombre+' · '+x.clase+' · '+x.poder).join('\n')+`\n\nPoder: ${sol.reduce((s,x)=>s+x.poder,0)}\nComplejidad: exponencial con poda`;
  }else if(type==='graph'){
    const adj=chars.map((c,i)=>chars.map((d,j)=>i!==j&&Math.abs(c.poder-d.poder)<=120?j:-1).filter(x=>x>=0));
    let q=[0],v=new Set([0]),order=[];
    while(q.length){let u=q.shift();order.push(chars[u].nombre);adj[u].forEach(w=>{if(!v.has(w)){v.add(w);q.push(w)}})}
    out.textContent=`GRAFOS — BFS\n\nRegla: arista si |poderA-poderB| <= 120.\nRecorrido desde Saber:\n${order.join(' → ')}\n\nDijkstra: cada arista cuesta 1; las distancias representan saltos mínimos.`;
  }else if(type==='huffman'){
    const text=chars[0].historia;const freq={};
    for(const ch of text)freq[ch]=(freq[ch]||0)+1;
    out.textContent=`HUFFMAN — HISTORIA DE ${chars[0].nombre}\n\nTexto: ${text}\n\nCaracteres distintos: ${Object.keys(freq).length}\nBits originales: ${text.length*8}\nEl árbol Huffman asigna códigos cortos a los símbolos frecuentes.\nComplejidad: O(n log n)`;
  }
}

/* ---------- VIDEO LIFECYCLE ---------- */
function setupVideos(){
  const opening=$('#openingVideo');
  const ending=$('#endingVideo');

  // Opening: intentar reproducción con audio. Los navegadores pueden bloquear
  // autoplay con sonido; en ese caso el botón "ENTRAR" reanuda con sonido.
  opening.muted=false;
  opening.volume=1.0;
  const p=opening.play();
  if(p) p.catch(()=>{});

  opening.addEventListener('ended',()=>{
    opening.pause();
    opening.currentTime=0;
    $('#opening').classList.add('hidden');
  });

  ending.muted=false;
  ending.volume=1.0;
  ending.pause();

  ending.addEventListener('ended',()=>{
    ending.pause();
    ending.currentTime=0;
  });
}

function stopVideo(video){
  if(!video)return;
  video.pause();
  video.currentTime=0;
  video.muted=true;
  video.volume=0;
}

function closeOpening(){
  const v=$('#openingVideo');
  v.muted=false;
  v.volume=1.0;
  const p=v.play();
  if(p) p.catch(()=>{});
  $('#opening').classList.add('hidden');
}

function skipOpening(){
  stopVideo($('#openingVideo'));
  $('#opening').classList.add('hidden');
}

function salir(){
  // Stop anything from the opening before showing the ending.
  stopVideo($('#openingVideo'));

  const overlay=$('#ending');
  const v=$('#endingVideo');
  overlay.classList.remove('hidden');
  v.muted=false;
  v.volume=1.0;
  v.currentTime=0;
  const p=v.play();
  if(p) p.catch(()=>{});
}

function volver(){
  // Important: stop the ending BEFORE returning/reloading, so its audio cannot
  // remain active or overlap with the next opening.
  stopVideo($('#endingVideo'));
  $('#ending').classList.add('hidden');
  location.reload();
}

$('#search')?.addEventListener('input',render);
load();
