<template>

  <form>
    <div class="container">
      <canvas ref="signatureCanvas" class="canvas">CANVAS</canvas>
    </div>
  </form>

</template>

<script lang="ts">

import Vue from 'vue'
import {Component} from "vue-property-decorator";

@Component
export default class Canvas extends Vue {
  private signatureCanvas!: HTMLCanvasElement;
  private context!: CanvasRenderingContext2D;
  private isDrawing = false;
  private lastX = 0;
  private lastY = 0;
  private lines: Array<{ x: number; y: number }> = [];

  mounted() {
    this.signatureCanvas = this.$refs.signatureCanvas as HTMLCanvasElement;
    this.context = this.signatureCanvas.getContext("2d")!;
    this.context.strokeStyle = "black";
    this.context.lineWidth = 2;
    this.context.lineJoin = "round";
    this.context.lineCap = "round";
    this.signatureCanvas.addEventListener("mousedown", this.onMouseDown);
    this.signatureCanvas.addEventListener("mouseup", this.onMouseUp);
    this.signatureCanvas.addEventListener("mousemove", this.onMouseMove);
  }

  private onMouseDown(event: MouseEvent) {
    this.isDrawing = true;
    this.lastX = event.clientX;
    this.lastY = event.clientY;
    this.context.strokeStyle = 'black';
  }

  private onMouseUp(event: MouseEvent) {
    this.isDrawing = false;
    this.lines.push({x: -1, y: -1});
  }

  private onMouseMove(event: MouseEvent) {
    if (!this.isDrawing) return;
    this.context.beginPath();
    this.context.moveTo(this.lastX, this.lastY);
    this.context.lineTo(event.clientX, event.clientY);
    this.context.stroke();
    this.lines.push({x: event.clientX, y: event.clientY});
    this.lastX = event.clientX;
    this.lastY = event.clientY;
  }

  toDataUrl() {
    return this.signatureCanvas.toDataURL();
  }

  toLines() {
    return this.lines;
  }
}

</script>

<style lang="scss">

.container {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;

  .canvas {
    width: 80%;
    height: 70%;
    border: 2px solid black;
    border-radius: 5px;
    background-color: palegoldenrod;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

</style>
