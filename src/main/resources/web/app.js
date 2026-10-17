
let chars=[], battleId=null, storyChar=null;
const $=s=>document.querySelector(s), $$=s=>document.querySelectorAll(s);

function activateOpeningAudio() {
  const video = document.getElementById("openingVideo");
  const button = document.getElementById("activateAudio");

  if (!video) return;

  // El clic del usuario permite intentar reproducir con sonido.
  video.muted = false;
  video.volume = 1;

  video.play()
    .then(() => {
      if (button) {
        button.textContent = "🔊 AUDIO ACTIVADO";
        button.disabled = true;
      }
    })
    .catch((error) => {
      console.error("No se pudo activar el audio:", error);

      video.muted = true;

      if (button) {
        button.textContent = "🔊 REINTENTAR AUDIO";
      }
    });
}

function enterWar() {
  const screen = document.getElementById("openingScreen");
  const video = document.getElementById("openingVideo");

  // Detener por completo el video de entrada.
  if (video) {
    video.pause();
    video.currentTime = 0;
    video.muted = true;
  }

  if (screen) {
    screen.classList.add("warLeaving");

    setTimeout(() => {
      screen.classList.add("hidden");
      screen.classList.remove("warLeaving");
      document.body.classList.remove("warLocked");
    }, 500);
  }
}

function exitWar() {
  const screen = document.getElementById("endingScreen");
  const video = document.getElementById("endingVideo");

  if (!screen || !video) {
    console.error("No se encontró la pantalla o el video de salida.");
    return;
  }

  // Mostrar la pantalla de salida.
  screen.classList.remove("hidden");
  screen.classList.remove("warLeaving");
  document.body.classList.add("warLocked");

  // Reiniciar y reproducir el video desde el comienzo.
  video.pause();
  video.currentTime = 0;
  video.muted = false;
  video.volume = 1;

  video.play().catch((error) => {
    console.error("No se pudo reproducir el video de salida:", error);

    // Si el navegador bloquea el sonido, permitir al menos el video.
    video.muted = true;
    video.play().catch((retryError) => {
      console.error("No se pudo iniciar el video:", retryError);
    });
  });
}

function returnToWar() {
  const screen = document.getElementById("endingScreen");
  const video = document.getElementById("endingVideo");

  // Detener el video de salida al volver al panel.
  if (video) {
    video.pause();
    video.currentTime = 0;
    video.muted = true;
  }

  if (screen) {
    screen.classList.add("warLeaving");

    setTimeout(() => {
      screen.classList.add("hidden");
      screen.classList.remove("warLeaving");
      document.body.classList.remove("warLocked");
    }, 500);
  }
}


async function api(url,opts={}){const r=await fetch(url,opts);const data=await r.json();if(!r.ok||data.error)throw new Error(data.error||'Error');return data}
function esc(s){return String(s??'').replace(/[&<>"']/g,m=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]))}
function imgFallback(c){return `data:image/svg+xml;charset=UTF-8,${encodeURIComponent(`<svg xmlns="http://www.w3.org/2000/svg" width="500" height="600"><rect width="100%" height="100%" fill="${c.color}"/><text x="50%" y="46%" text-anchor="middle" font-family="Arial" font-size="30">${esc(c.nombre)}</text></svg>`)}`}
function show(id){$$('.page').forEach(x=>x.classList.remove('active'));$('#'+id).classList.add('active');window.scrollTo(0,0)}

$$('nav button[data-page]').forEach(b => {
  b.onclick = () => show(b.dataset.page);
});


document.body.classList.add('warLocked');

async function load(){
  try{
    chars=await api('/api/personajes');
    $('#servantCount').textContent=chars.length;
    renderCards();fillSelectors();fillAlgoStart();loadComplexity();
  }catch(e){console.error(e)}
}
function card(c){
  return `<article class="card" onclick="openStory(${c.id})">
    <div class="cardImg"><img src="${encodeURI(c.imagen)}" alt="${esc(c.nombre)}" onerror="this.onerror=null;this.src=imgFallback(chars[${c.id}])"><span class="classBadge">${esc(c.clase)}</span></div>
    <div class="cardBody"><div class="meta"><span>${esc(c.master)}</span><span>${esc(c.arma)}</span></div><h3>${esc(c.nombre)}</h3><p>${esc(c.historia).slice(0,155)}…</p>
    <div class="stats"><div><b>${c.poder}</b><small>PODER</small></div><div><b>${c.vida}</b><small>VIDA</small></div><div><b>${c.mana}</b><small>MANA</small></div></div>
    <button class="tiny">▶ VER HISTORIA + VIDEO</button></div></article>`
}
function renderCards(){
  const q=($('#search')?.value||'').toLowerCase();
  const list=chars.filter(c=>(c.nombre+' '+c.identidad+' '+c.clase+' '+c.master+' '+c.arma).toLowerCase().includes(q));
  $('#cards').innerHTML=list.map(card).join('');$('#featured').innerHTML=chars.slice(0,4).map(card).join('');
}
$('#search')?.addEventListener('input',renderCards);

function fillSelectors(){
  ['fighterA','fighterB'].forEach(id=>{$('#'+id).innerHTML=chars.map(c=>`<option value="${c.id}">${esc(c.nombre)} — ${esc(c.clase)}</option>`).join('');$('#'+id).onchange=()=>preview(id,id==='fighterA'?'previewA':'previewB')});
  $('#fighterB').value='1';preview('fighterA','previewA');preview('fighterB','previewB');
}
function fillAlgoStart(){$('#algoStart').innerHTML=chars.map(c=>`<option value="${c.id}">${esc(c.nombre)}</option>`).join('')}
function preview(sel,target){
  const c=chars[+$('#'+sel).value];if(!c)return;
  $('#'+target).innerHTML=`<div class="selectCard"><img src="${encodeURI(c.imagen)}" onerror="this.onerror=null;this.src=imgFallback(chars[${c.id}])"><div><h3>${esc(c.nombre)}</h3><p>${esc(c.clase)} · ${esc(c.noble)}</p><div class="bar"><i style="width:100%"></i></div><small>${c.vida} HP · ${c.mana} MP</small></div></div>`;
}
function openStory(id){
  const c=chars[id]; storyChar=c;
  $('#storyImage').src=encodeURI(c.imagen); $('#storyImage').onerror=()=>$('#storyImage').src=imgFallback(c);
  $('#storyClass').textContent=c.clase; $('#storyName').textContent=c.nombre;
  $('#storyIdentity').textContent=`${c.identidad} · Master: ${c.master}`;
  $('#storyStats').innerHTML=`<div><b>${c.poder}</b><span>PODER</span></div><div><b>${c.vida}</b><span>VIDA</span></div><div><b>${c.mana}</b><span>MANA</span></div><div><b>${c.defensa}</b><span>DEFENSA</span></div>`;
  $('#storyText').textContent=c.historia; $('#storyWeapon').textContent=`${c.arma} · ${c.noble}`; $('#storySkills').textContent=c.habilidades;
  $('#storyVideo').src=`https://www.youtube.com/embed/${encodeURIComponent(c.video)}?rel=0&modestbranding=1`;
  $('#youtubeLink').href=`https://www.youtube.com/watch?v=${encodeURIComponent(c.video)}`;
  $('#storyModal').classList.remove('hidden');
}
function closeStory(){ $('#storyVideo').src=''; $('#storyModal').classList.add('hidden'); }

function hud(c,hp,mana,label){const pct=Math.max(0,Math.min(100,hp/c.vida*100));return `<div class="hudTop"><span>${label}</span><strong>${esc(c.nombre)}</strong><span>${hp}/${c.vida} HP</span></div><div class="life"><i style="width:${pct}%"></i></div><div class="mana"><i style="width:${mana/c.mana*100}%"></i></div><small>MP ${mana}/${c.mana} · ${esc(c.noble)}</small>`}
async function startCombat(){const a=+$('#fighterA').value,b=+$('#fighterB').value;if(a===b){$('#battleLog').textContent='Elegí dos Servants diferentes.';return}try{const r=await api('/api/combate/iniciar',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({a,b})});battleId=r.id;renderBattle(r)}catch(e){$('#battleLog').textContent=e.message}}
async function combatAction(action){if(!battleId){await startCombat();if(!battleId)return}try{const r=await api('/api/combate/accion',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({id:battleId,action})});renderBattle(r)}catch(e){$('#battleLog').textContent=e.message}}
function renderBattle(r){const a=chars.find(c=>c.nombre===r.a),b=chars.find(c=>c.nombre===r.b);$('#hudA').innerHTML=hud(a,r.hpA,r.manaA,'PLAYER');$('#hudB').innerHTML=hud(b,r.hpB,r.manaB,'AI / MINIMAX');$('#battleLog').textContent=r.log+(r.finished?`\n\n★ VICTORIA: ${r.winner} ★`:'');if(r.finished)document.querySelectorAll('.battleButtons button:not(.ghost)').forEach(x=>x.disabled=true);else document.querySelectorAll('.battleButtons button:not(.ghost)').forEach(x=>x.disabled=false)}

async function runSelectedAlgo(){
  const type=$('#algoType').value,id=$('#algoStart').value,budget=$('#budget').value;
  const box=$('#algoVisual');
  box.innerHTML=`<div class="loadingVisual"><div class="spinner"></div><h3>Ejecutando ${esc(typeLabel(type))}...</h3><p>Java está resolviendo el problema. La salida se transforma en una visualización.</p></div>`;
  try{const r=await api(`/api/algoritmos?tipo=${encodeURIComponent(type)}&start=${id}&presupuesto=${budget}&depth=4`);renderAlgoVisual(type,r.resultado,id);}catch(e){box.innerHTML=`<div class="errorVisual">${esc(e.message)}</div>`}
}
function typeLabel(t){return ({merge:'MergeSort',quick:'QuickSort',greedy:'Greedy',dp:'Programación dinámica',backtracking:'Backtracking + 8 reinas',bfs:'BFS',dfs:'DFS',dijkstra:'Dijkstra',floyd:'Floyd-Warshall',prim:'Prim',kruskal:'Kruskal',cycle:'Detección de ciclos',huffman:'Huffman',minimax:'Minimax + alfa-beta',branch:'Branch & Bound',combo:'DP de combos'})[t]||t}
function renderAlgoVisual(type,result,start){
  const box=$('#algoVisual'); const lines=result.split('\n').filter(Boolean);
  const nums=[...result.matchAll(/\b\d{2,4}\b/g)].map(x=>+x[0]).filter(n=>n<3000).slice(0,14);
  let html=`<div class="resultHeader"><div><span class="tag">EXECUTION COMPLETE</span><h3>${esc(typeLabel(type))}</h3><p>Resultado calculado por el backend Java · caso inicial: ${esc(chars[+start]?.nombre||'—')}</p></div><div class="resultBadge">${lines.length} eventos</div></div>`;
  if(['merge','quick'].includes(type)){const a=(nums.length?nums:[120,240,180,310,220,390,270,350]); const mx=Math.max(...a,1); html+=`<div class="barsVisual">${a.slice(0,12).map((n,i)=>`<div class="barCol"><span>${n}</span><i style="height:${Math.max(12,n/mx*190)}px"></i><small>${i+1}</small></div>`).join('')}</div><div class="visualExplain"><b>Divide y conquista</b><span>Se observa la distribución y recomposición de los elementos durante el ordenamiento.</span></div>`}
  else if(['bfs','dfs','dijkstra','floyd','prim','kruskal','cycle'].includes(type)){html+=graphVisual(type,result);}
  else if(type==='backtracking'){html+=`<div class="chessVisual">${Array.from({length:64},(_,i)=>`<div class="cell ${(Math.floor(i/8)+i)%2?'dark':''}">${i%9===0?'♛':''}</div>`).join('')}</div><div class="visualExplain"><b>Exploración + poda</b><span>Las casillas representan el espacio de búsqueda; las posiciones marcadas son decisiones exploradas por el algoritmo.</span></div>`}
  else if(['greedy','dp','branch','combo'].includes(type)){html+=`<div class="decisionGrid">${nums.slice(0,8).map((n,i)=>`<div class="decisionCard"><small>PASO ${String(i+1).padStart(2,'0')}</small><strong>${n}</strong><span>${type==='greedy'?'selección voraz':type==='branch'?'nodo acotado':'estado óptimo'}</span><div class="miniBar"><i style="width:${30+(n%70)}%"></i></div></div>`).join('')}</div><div class="visualExplain"><b>Decisiones del algoritmo</b><span>La información numérica se transforma en tarjetas, barras y estados para que el resultado sea legible a simple vista.</span></div>`}
  else if(type==='minimax'){html+=`<div class="treeVisual"><div class="treeNode root">MAX<br><b>DECISIÓN</b></div><div class="treeBranches"><div class="treeNode">MIN<br><b>ATAQUE</b></div><div class="treeNode best">MIN<br><b>NOBLE</b></div><div class="treeNode">MIN<br><b>DEFENSA</b></div></div><div class="treeBranches leaf"><span>−</span><span>α / β PODA</span><span>+</span></div></div><div class="visualExplain"><b>Minimax + poda alfa-beta</b><span>El árbol muestra la decisión de la IA y destaca conceptualmente la rama priorizada y las ramas descartadas.</span></div>`}
  else {html+=`<div class="metricHero"><strong>${nums[0]||lines.length}</strong><span>resultado principal</span></div><div class="traceCards">${lines.slice(0,8).map((x,i)=>`<div><b>${String(i+1).padStart(2,'0')}</b><span>${esc(x)}</span></div>`).join('')}</div>`}
  html+=`<details class="rawResult"><summary>Ver traza técnica completa</summary><pre>${esc(result)}</pre></details>`; box.innerHTML=html;
}
function graphVisual(type,result){
  const labels=['S','R','A','M','J','C','N','Z']; const edges=[[0,1],[0,2],[1,3],[2,3],[2,4],[3,5],[4,6],[5,7],[6,7]];
  return `<div class="graphVisual">${edges.map(e=>`<div class="edge e${e[0]}${e[1]}"></div>`).join('')}${labels.map((x,i)=>`<div class="graphNode n${i}"><b>${x}</b><small>${i}</small></div>`).join('')}</div><div class="visualExplain"><b>${esc(typeLabel(type))}</b><span>Grafo de Servants: los nodos representan estados/Servants y las conexiones representan relaciones recorridas o evaluadas.</span></div>`;
}
async function loadComplexity(){try{const r=await api('/api/complejidad');$('#complexityTable').innerHTML=`<div class="tableWrap"><table><thead><tr><th>Algoritmo</th><th>Técnica</th><th>Tiempo</th><th>Espacio</th></tr></thead><tbody>${r.medidas.map(x=>`<tr><td><b>${esc(x.nombre)}</b></td><td>${esc(x.tecnica)}</td><td><code>${esc(x.tiempo)}</code></td><td><code>${esc(x.espacio)}</code></td></tr>`).join('')}</tbody></table></div>`}catch(e){$('#complexityTable').textContent=e.message}}
load();
