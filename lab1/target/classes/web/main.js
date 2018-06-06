var wsUri;
var webSocket = null;
var scene;
var camera;
var renderer;
var start;
var material;
var controls = void 0;
var sphereGeom;
var hemiLight;
var data = void 0;
var tick;
var message;
var startData;
var sphereMesh = [];
var testCase = document.getElementById("testCase").value;


init();

function init() {
    initGraphics();
    initSockets();
}

function initSockets() {
    wsUri = "ws://" + window.location.hostname + ":" + window.location.port + "/connect/";
    console.log(wsUri);
    webSocket = new WebSocket(wsUri);
    webSocket.onerror = onError.bind(this);
    webSocket.onopen = onOpen.bind(this);
    webSocket.onmessage = onMessage.bind(this);
}

function initBalls() {
    sphereMesh.push(new THREE.Mesh(sphereGeom, material));
    sphereMesh.push(new THREE.Mesh(sphereGeom, material));

    sphereMesh[0].material.color = new THREE.Color(255, 0, 0);
    sphereMesh[1].material.color = new THREE.Color(0, 0, 255);
}

function initGraphics() {
    scene = new THREE.Scene();

    camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 100);
    camera.position.set(10, 5, 15);
    camera.lookAt(scene.position);

    scene.add(camera);

    renderer = new THREE.WebGLRenderer();
    renderer.setSize(window.innerWidth, window.innerHeight - 300);
    renderer.setClearColor(0xaaccff);
    renderer.shadowMap.enabled = true;
    renderer.toneMapping = THREE.ReinhardToneMapping;
    start = false;

    document.body.appendChild(renderer.domElement);

    sphereGeom = new THREE.SphereGeometry(1, 16, 8);
    material = new THREE.MeshStandardMaterial({
        roughness: 0.7,
        color: 0xffffff,
        bumpScale: 0.002,
        metalness: 0.2
    });

   

    initBalls();

    hemiLight = new THREE.HemisphereLight(0xd0d0e1, 0x0f0e0d, 0.2);

    scene.add(hemiLight);
    controls = new THREE.OrbitControls(camera, renderer.domElement);
    controls.update();
}

function animate() {
    requestAnimationFrame(animate.bind(this));

    if (start && tick < data.trajectoryBall1.length) {
        sphereMesh[0].position.x = data.trajectoryBall1[tick].x;
        sphereMesh[0].position.y = data.trajectoryBall1[tick].y;
        sphereMesh[0].position.z = data.trajectoryBall1[tick].z;

        sphereMesh[1].position.x = data.trajectoryBall2[tick].x;
        sphereMesh[1].position.y = data.trajectoryBall2[tick].y;
        sphereMesh[1].position.z = data.trajectoryBall2[tick].z;

        tick++;

    } else {
        start = false;
    }
    renderer.render(scene, camera);
}

function onError(evt) {
    console.log(evt.data);
}

function onOpen() {
    console.log("connected");
}


function onMessage(evt) {
    var obj = JSON.parse(evt.data);
    switch (obj.type) {
        case "response":
            data = JSON.parse(obj.data);
                sphereMesh[0].position.x = data.trajectoryBall1[0].x;
                sphereMesh[0].position.y = data.trajectoryBall1[0].y;
                sphereMesh[0].position.z = data.trajectoryBall1[0].z;

                sphereMesh[1].position.x = data.trajectoryBall2[0].x;
                sphereMesh[1].position.y = data.trajectoryBall2[0].y;
                sphereMesh[1].position.z = data.trajectoryBall2[0].z;

                scene.add(sphereMesh[0]);
                scene.add(sphereMesh[1]);


            start = true;
            tick = 0;
            break;
    }
}


function onClick() {
    testCase = document.getElementById("testCase").value;
    initBalls();
    startData = {
        testCase: testCase
    };

    message = {
        type: "request",
        data: JSON.stringify(startData)
    };
    webSocket.send(JSON.stringify(message));
    animate();
}
